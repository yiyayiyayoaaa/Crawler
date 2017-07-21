package cx.study.crawler.bean;

/**
 *  代理地址javabean
 * Created by xiao on 2017/7/21.
 */
public class ProxyAddress {

    private String address;
    private int port;

    public ProxyAddress(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
