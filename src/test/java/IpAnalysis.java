import cx.study.crawler.analysis.Analysis;
import cx.study.crawler.bean.ProxyAddress;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by xiao on 2017/7/21.
 */
public class IpAnalysis extends Analysis<ProxyAddress>{

    @Override
    public Elements analysis2String(String string,Object ... objects) {
        Document document = Jsoup.parse(string);
        return document.select("tr.odd");
    }

    @Override
    public ProxyAddress analysis2Bean(Element element,Object ... objects) {
        String address = element.child(1).text();
        int port = Integer.parseInt(element.child(2).text());
        return new ProxyAddress(address, port);
    }
}
