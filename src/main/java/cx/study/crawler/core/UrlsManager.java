package cx.study.crawler.core;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 *  Url地址管理
 * Created by xiao on 2017/7/21.
 */
public class UrlsManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Cache cache1; //保存需要抓取的url
    private Cache cache2; //保存已经抓取的url
    private CacheManager cacheManager;
    private LinkedList<Object> urlList = new LinkedList<>();
    public UrlsManager(){
        cacheManager = CacheManager.create("./src/main/resources/ehcache.xml");
        cache1 = cacheManager.getCache("url1");
        cache2 = cacheManager.getCache("url2");


    }

    /**
     *  初始化urlList 从缓存中获取需要抓取的Url;
     */
    private void initUrlList(){
        urlList.addAll(cache1.getKeys());
    }

    public void stop(){
        cache1.flush();
        cache2.flush();
        cacheManager.shutdown();
    }

    /**
     * 优先从urlList获取数据 如果没有数据 则从缓存中加载数据到urlList
     * @return url
     */
    public String getUrl(){
        if (urlList.size() == 0) {
            initUrlList();
        }
        String url = (String) urlList.pop();
        if (url != null) {
            cache1.remove(url);
        }
        return url;
    }

    public void addBaseUrl(String url){
        Element element = new Element(url,null);
        cache1.put(element);
        cache1.flush();
    }

    public void addUrl(String url){
        Element element = cache2.get(url);
        if (element == null){
            element = new Element(url,null);
            cache1.put(element);
            cache1.flush();
        }
    }

    /**
     * 访问成功后加入缓存
     * @param url String
     */
    public void urlSuccess(String url){
        Element element = new Element(url, null);
        cache2.put(element);
        cache2.flush();
        logger.info("访问成功：" + url);

    }

    public int count(){
        logger.info("已爬去地址数量：" + cache2.getSize());
        return cache1.getSize();
    }
}
