import cx.study.crawler.analysis.Analysis;
import jdk.nashorn.internal.objects.NativeUint8Array;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.HTML;

/**
 *
 * Created by xiao on 2017/7/21.
 */
public class BeanAnalysis extends Analysis<Bean>{

    @Override
    public Elements analysis2String(String string,Object ... objects) {
        Document document = Jsoup.parse(string);
        return document.select("table[width=100%] tr");
    }

    @Override
    public Bean analysis2Bean(Element element,Object ... objects) {
        Elements children = element.children();
        Bean bean = null;
        if (children.size() == 3 && "#FFFFFF".equals(element.child(0).attr("bgcolor"))){
            String base = "https://xingzhengquhua.51240.com";
            Element tagA = element.child(0).child(0);
            String href = tagA.attr("href");
            String name = tagA.text();
            String code = element.child(1).child(0).text();
            String url = ((String) objects[0]).replace(base,"").replace("/","");
            System.out.println(url);
            bean = new Bean(code,url,name);
            urlsManager.addUrl(base + href);
        }
        return bean;
    }
}
