package cx.study.crawler.core;

import java.util.List;

/**
 *  数据处理  对解析的javabean进行操作
 * Created by xiao on 2017/7/21.
 */
public interface DataHandle<T> {

    /**
     * 数据处理
     * @param t
     */
    void handle(T t);
    void handle(List<T> list);
}
