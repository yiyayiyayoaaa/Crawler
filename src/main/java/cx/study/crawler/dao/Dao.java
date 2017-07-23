package cx.study.crawler.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.Objects;

/**
 *
 * Created by xiao on 2017/7/22.
 */
public class Dao<T>{

    private SessionFactory sessionFactory;
    public Dao(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public boolean save(List<T> list){
        if (list == null || list.size() == 0) {
            return false;
        }
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            list.stream().filter(Objects::nonNull).forEach(session::saveOrUpdate);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
