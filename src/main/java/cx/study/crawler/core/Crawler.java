package cx.study.crawler.core;

import cx.study.crawler.analysis.Analysis;
import cx.study.crawler.http.HttpClient;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * Created by xiao on 2017/7/21.
 */
public class Crawler<T>{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private HttpClient httpClient;
    private UrlsManager urlsManager;
    private Analysis<T> analysis;
    private DataHandle<T> dataHandle;

    private boolean isStop = true;
    public Crawler(HttpClient httpClient, Analysis<T> analysis, UrlsManager urlsManager, DataHandle<T> dataHandle){
        this.httpClient = httpClient;
        this.urlsManager = urlsManager;
        this.analysis = analysis;
        this.dataHandle = dataHandle;
        this.analysis.setUrlsManager(urlsManager);
        this.httpClient.setUrlsManager(urlsManager);
    }

    /**
     * 开启爬虫
     */
    public void start(){
        isStop = false;
        while (!isStop) {
            String url = urlsManager.getUrl();
            if (url != null) {
                Observable.create(o -> {
                    String html = httpClient.get(url);
                    if (html != null){
                        o.onNext(html);
                    }
                }).observeOn(Schedulers.io()).subscribe(html->{
                    dataHandle.handle(analysis.analysis(html.toString(),url));
                    logger.info("待爬取url数量：" + urlsManager.count());
                });
            } else {
                logger.info("暂时没有可以爬取的URL！");
                sleep(2000);
            }
        }
    }

    private void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        isStop = true;
    }
}
