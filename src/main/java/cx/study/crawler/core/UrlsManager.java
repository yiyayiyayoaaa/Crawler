package cx.study.crawler.core;

import java.util.LinkedList;
import java.util.List;

/**
 *  Url地址管理
 * Created by xiao on 2017/7/21.
 */
public class UrlsManager {

    private LinkedList<String> urls;

    public UrlsManager(){
        urls = new LinkedList<>();
    }

    public String getUrl(){
        if (urls.size() > 0) {
            return urls.pop();
        }
        return null;
    }

    public void addUrl(String url){
        urls.addLast(url);
    }

    public void addUrls(List<String> urls) {
        this.urls.addAll(urls);
    }

    public int count(){
        return urls.size();
    }
}
