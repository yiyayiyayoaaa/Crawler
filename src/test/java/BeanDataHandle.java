import cx.study.crawler.core.DataHandle;
import cx.study.crawler.dao.Dao;

import java.util.List;

/**
 *
 * Created by xiao on 2017/7/21.
 */
public class BeanDataHandle implements DataHandle<Bean> {
    private Dao<Bean> dao = new Dao<>();

    @Override
    public boolean handle(Bean bean) {
        return false;
    }

    @Override
    public boolean handle(List<Bean> list) {
        return dao.save(list);
    }
}
