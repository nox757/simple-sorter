package hibernate.dao;

import hibernate.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.io.Serializable;

abstract class AbstractDAO<T, P extends Serializable> implements DAO<T, P> {

    protected abstract Class<T> getEntityClass();

    @Override
    public P create(T object) {
        Session session = null;
        P id = null;
        try {
            session = HibernateSessionUtil.getSessionFactory().openSession();
            session.beginTransaction();
            id = (P) session.save(object);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return id;
    }

    @Override
    public T read(P id) {
        T result = null;
        Session session = null;
        try {
            session = HibernateSessionUtil.getSessionFactory().openSession();
            session.beginTransaction();
            result = session.get(getEntityClass(), id);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public void update(T object) {
        Session session = null;
        try {
            session = HibernateSessionUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(object);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void delete(T object) {
        Session session = null;
        try {
            session = HibernateSessionUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(object);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
