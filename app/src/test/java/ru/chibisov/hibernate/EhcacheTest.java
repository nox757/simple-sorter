package ru.chibisov.hibernate;

import dai.CountryDao;
import entities.Country;
import hibernate.dao.CountryDaoImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EhcacheTest extends BaseTest {

    private final static String EHCACHE_PROPERTY_NAME = "hibernate_ehcache.properties";
    private Country firstCountry;
    private  CountryDao countryDao;

    @Override
    protected String getNamePropertyResource() {
        return EHCACHE_PROPERTY_NAME;
    }

    @Before
    public void setUp() {
        countryDao = new CountryDaoImpl(sessionFactory);
        firstCountry = new Country();
        firstCountry.setName("Country");
        Long id = countryDao.create(firstCountry);
        firstCountry.setId(id);
        sessionFactory.getCache().evictAllRegions();
    }

    @Test
    public void ehcacheHibernateTest() {
        Country country = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            country = session.get(Country.class, firstCountry.getId());
            assertEquals(firstCountry.getName(), country.getName());
            transaction.commit();

            //Check first level cache in one Session
            transaction = session.beginTransaction();
            country = session.get(Country.class, firstCountry.getId());
            assertEquals(firstCountry.getName(), country.getName());
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && (transaction.getStatus() == TransactionStatus.ACTIVE
                    || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK)) {
                transaction.rollback();
            }
            throw ex;
        }
        assertEquals(0, sessionFactory.getStatistics().getSecondLevelCacheHitCount());
        assertEquals(1, sessionFactory.getStatistics().getEntityLoadCount());

        //Check second level cache in other session
        transaction = null;
        Country country2 = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            country2 = session.get(Country.class, firstCountry.getId());
            assertEquals(firstCountry.getName(), country2.getName());
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && (transaction.getStatus() == TransactionStatus.ACTIVE
                    || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK)) {
                transaction.rollback();
            }
            throw ex;
        }
        assertEquals(1, sessionFactory.getStatistics().getSecondLevelCacheHitCount());
        assertEquals(1, sessionFactory.getStatistics().getEntityLoadCount());

    }

    @After
    public void clear() {
        countryDao.delete(firstCountry);
    }
}
