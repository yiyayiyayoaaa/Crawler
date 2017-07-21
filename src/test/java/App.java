import cx.study.crawler.core.Crawler;
import cx.study.crawler.core.UrlsManager;
import cx.study.crawler.http.HttpClient;

import java.io.IOException;

/**
 *
 * 1.网络请求
 * 2.数据解析 2.1 主数据解析  2.2 代理ip解析
 * 3.数据持久化
 * Created by xiao on 2017/7/21.
 */
public class App {
    public static void main(String[] args) throws IOException {
        HttpClient httpClient = new HttpClient();
        IpAnalysis ipAnalysis = new IpAnalysis();
        String baseUrl = "https://xingzhengquhua.51240.com/";
        BeanAnalysis analysis = new BeanAnalysis();
        //analysis.analysis(httpClient.get(baseUrl));
        httpClient.setAddressAnalysis(ipAnalysis,"http://www.xicidaili.com/nn"); //配置代理ip网址 和代理ip解析器
        UrlsManager urlsManager = new UrlsManager();
        urlsManager.addUrl(baseUrl);
        Crawler<Bean> beanCrawler = new Crawler<>(httpClient,analysis,urlsManager,new BeanDataHandle());
        beanCrawler.start();

    }
}


