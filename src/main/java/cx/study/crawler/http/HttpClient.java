package cx.study.crawler.http;

import cx.study.crawler.analysis.Analysis;
import cx.study.crawler.bean.ProxyAddress;
import cx.study.crawler.core.UrlsManager;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by xiao on 2017/7/21.
 */
public class HttpClient{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private  OkHttpClient okHttpClient;
    private LinkedList<ProxyAddress> proxyAddresses; //代理ip
    private Analysis<ProxyAddress>  addressAnalysis;
    private String proxyUrl;
    private UrlsManager urlsManager;
    /**
     * 添加代理解析对象
     * @param addressAnalysis Analysis<ProxyAddress>
     */
    public void setAddressAnalysis(Analysis<ProxyAddress> addressAnalysis, String proxyUrl) {
        this.addressAnalysis = addressAnalysis;
        this.proxyUrl = proxyUrl;
    }

    public void setUrlsManager(UrlsManager urlsManager) {
        this.urlsManager = urlsManager;
    }
    /**
     * 超时时长,单位秒
     */
    private static final long TIME_OUT = 10;

    private OkHttpClient getHttpClient() {
        if (okHttpClient == null) {
            synchronized (HttpClient.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
                    builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
                    builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
                    okHttpClient = builder.build();
                }
            }
        }
        return okHttpClient;
    }

    /**
     * 更改代理地址
     */
    private void updateProxy(){
        if (proxyAddresses == null) {
            proxyAddresses = new LinkedList<>();
        }
        if (proxyAddresses.size() < 15 && addressAnalysis != null) {
            //发送请求 获取代理地址
            logger.info("正在获取代理地址......");
            proxyAddresses.clear();
            okHttpClient = getHttpClient().newBuilder().proxy(null).build();
            List<ProxyAddress> analysis = addressAnalysis.analysis(get(proxyUrl));
            proxyAddresses.addAll(analysis);
        }
        ProxyAddress proxyAddress = proxyAddresses.pop();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress.getAddress(), proxyAddress.getPort()));
        okHttpClient = getHttpClient().newBuilder().proxy(proxy).build();
        logger.info("切换代理中......剩余代理ip：" + proxyAddresses.size());
    }

    /**
     * 发送get请求
     * @param url 请求地址
     * @return Response
     */
    public String get(String url){
        logger.info("正在爬取：" + url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
                .build();
        Call call = getHttpClient().newCall(request);
        String html = null;
        Response response = null;
        try {
            response = call.execute();
            if (response.code() == 200) {
                logger.info("请求成功：" + response.code());
                html = response.body().string();
                urlsManager.urlSuccess(url);//访问成功的url
            } else {
                //访问失败 将地址添加回队列
                logger.error("请求失败：" + response.code());
                updateProxyAndRestUrl(url);
            }
        } catch (Exception e) {
            logger.error("请求失败：" + e.getMessage());
            updateProxyAndRestUrl(url);
        } finally {
            if (response!= null) {
                response.close();
            }
        }
        return html ;
    }

    private void updateProxyAndRestUrl(String url){
        updateProxy();
        urlsManager.addUrl(url);
    }
}
