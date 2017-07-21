package cx.study.crawler.analysis;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by xiao on 2017/7/21.
 */
public abstract class Analysis<T> {

    /**
     *  解析网页信息
     * @param html String
     * @return List<T>
     */
    public List<T> analysis(String html){
        if (html == null) {
            return null;
        }
        Elements elements = analysis2String(html);
        List<T> list = new ArrayList<>();
        elements.forEach(e -> list.add(analysis2Bean(e)));
        return list;
    }

    /**
     * 将网页信息解析成节点
     * @param string String 网页信息
     * @return JSoup Elements
     */
    public abstract Elements analysis2String(String string);

    /**
     *  处理节点  将节点解析成实体对象
     * @param element JSoup Element
     * @return 实体对象
     */
    public abstract T analysis2Bean(Element element);
}
