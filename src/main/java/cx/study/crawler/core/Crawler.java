package cx.study.crawler.core;

import cx.study.crawler.analysis.Analysis;
import cx.study.crawler.http.HttpClient;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;


/**
 *
 * Created by xiao on 2017/7/21.
 */
public class Crawler<T>{

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
                String html = httpClient.get(url);
                List<T> list = this.analysis.analysis(html, url);
                dataHandle.handle(list);
            } else {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("暂时没有可以爬取的URL！");
            }
        }
    }

    public void stop(){
        isStop = true;
    }
}
