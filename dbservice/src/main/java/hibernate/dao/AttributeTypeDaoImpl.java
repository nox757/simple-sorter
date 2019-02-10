package hibernate.dao;

import hibernate.dao.interfeces.AttributeTypeDao;
import hibernate.entities.AttributeType;
import org.hibernate.SessionFactory;

public class AttributeTypeDaoImpl extends AbstractDAO<AttributeType, Long> implements AttributeTypeDao {

    public AttributeTypeDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<AttributeType> getEntityClass() {
        return AttributeType.class;
    }
}
