package com.itmo.kotiki.dao;

import com.itmo.kotiki.entity.BaseEntity;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.entity.KittyFriendship;
import com.itmo.kotiki.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public abstract class BaseDao<TEntity extends BaseEntity> implements Dao {
    private SessionFactory sessionFactory;
    private Session currentSession;
    private Transaction currentTransaction;

    public BaseDao() {
        configureSessionFactory();
    }

    private SessionFactory getSessionFactory() { return sessionFactory; }

    private void configureSessionFactory() {
        var configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Kitty.class);
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(KittyFriendship.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(BaseEntity entity) {
        getCurrentSession().save(entity);
    }

    public void update(BaseEntity entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    public abstract TEntity findById(Long id);

    public void delete(BaseEntity entity) {
        getCurrentSession().delete(entity);
    }

    public abstract List<TEntity> findAll();

    public void deleteAll() {
        List<TEntity> entityList = findAll();
        for (TEntity entity : entityList) {
            delete(entity);
        }
    }
}
