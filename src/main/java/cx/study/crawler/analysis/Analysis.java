package cx.study.crawler.analysis;

import cx.study.crawler.core.UrlsManager;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * Created by xiao on 2017/7/21.
 */
public abstract class Analysis<T> {

    /**
     *  Urls管理器  保存url
     */
    protected UrlsManager urlsManager;

    public void setUrlsManager(UrlsManager urlsManager) {
        this.urlsManager = urlsManager;
    }
    /**
     *  解析网页信息
     * @param html String
     * @return List<T>
     */
    public List<T> analysis(String html,Object ... objects){
        if (html == null) {
            return null;
        }
        Elements elements = analysis2String(html,objects);
        List<T> list = new ArrayList<>();
        elements.forEach(e -> list.add(analysis2Bean(e, objects)));
        return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 将网页信息解析成节点
     * @param string String 网页信息
     * @return JSoup Elements
     */
    public abstract Elements analysis2String(String string,Object ... objects);

    /**
     *  处理节点  将节点解析成实体对象
     * @param element JSoup Element
     * @return 实体对象
     */
    public abstract T analysis2Bean(Element element,Object ... objects);
}
