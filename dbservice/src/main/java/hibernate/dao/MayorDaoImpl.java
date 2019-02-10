package hibernate.dao;

import hibernate.dao.interfeces.MayorDao;
import hibernate.entities.Mayor;
import org.hibernate.SessionFactory;

public class MayorDaoImpl extends AbstractDAO<Mayor, Long> implements MayorDao {

    public MayorDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Mayor> getEntityClass() {
        return Mayor.class;
    }
}
