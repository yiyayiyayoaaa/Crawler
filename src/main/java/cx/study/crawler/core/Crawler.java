package cx.study.crawler.core;

import cx.study.crawler.analysis.Analysis;
import cx.study.crawler.http.HttpClient;

/**
 *
 * Created by xiao on 2017/7/21.
 */
public class Crawler<T>{

    private HttpClient httpClient;
    private UrlsManager urlsManager;
    private Analysis<T> analysis;
    private DataHandle<T> dataHandle;

    public Crawler(HttpClient httpClient, Analysis<T> analysis, UrlsManager urlsManager, DataHandle<T> dataHandle){
        this.httpClient = httpClient;
        this.urlsManager = urlsManager;
        this.analysis = analysis;
        this.dataHandle = dataHandle;
    }



}
