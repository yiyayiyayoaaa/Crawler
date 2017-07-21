import cx.study.crawler.core.DataHandle;

import java.util.List;

/**
 * Created by xiao on 2017/7/21.
 */
public class BeanDataHandle implements DataHandle<Bean> {
    @Override
    public void handle(Bean bean) {

    }

    @Override
    public void handle(List<Bean> list) {
        System.out.println(list);
    }
}
