package hibernate.dao;

import hibernate.dao.interfeces.DAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

/**
 * Abstract class to provide CRUD operations
 * @param <T> type of mapping class object db
 * @param <ID> type of primary key
 */
abstract class AbstractDAO<T, ID extends Serializable> implements DAO<T, ID> {

    private final SessionFactory sessionFactory;

    /**
     *
     * @return
     */
    protected abstract Class<T> getEntityClass();

    public AbstractDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public ID create(T object) {
        Session session = null;
        ID id = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            id = (ID) session.save(object);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session != null) {
               rollbackTransaction(session);
            }
            throw new HibernateDdException(ex);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return id;
    }

    @Override
    public T read(ID id) {
        T result = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            result = session.get(getEntityClass(), id);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session != null) {
                rollbackTransaction(session);
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
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(object);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session != null) {
                rollbackTransaction(session);
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
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(object);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session != null) {
                rollbackTransaction(session);
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<T> getAll() {
        Session session = null;
        ID id = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(getEntityClass());
            criteria.from(getEntityClass());
            return session.createQuery(criteria).getResultList();
        } catch (Exception ex) {
            if (session != null) {
                rollbackTransaction(session);
            }
            throw new HibernateDdException(ex);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    protected void rollbackTransaction(Session session) {
        if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
            session.getTransaction().rollback();
        }
    }
}
