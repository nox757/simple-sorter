package hibernate.dao;

import dai.RegionDao;
import entities.Region;
import org.hibernate.SessionFactory;

public class RegionDaoImpl extends AbstractDAO<Region, Long> implements RegionDao {

    public RegionDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Region> getEntityClass() {
        return Region.class;
    }
}
