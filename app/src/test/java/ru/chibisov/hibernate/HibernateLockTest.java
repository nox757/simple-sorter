package ru.chibisov.hibernate;

import dai.CountryDao;
import dai.RegionDao;
import entities.Country;
import entities.Region;
import hibernate.dao.CountryDaoImpl;
import hibernate.dao.RegionDaoImpl;
import jdbc.connection.ConnectionFactory;
import jdbc.connection.ConnectionFactoryImpl;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.OptimisticLockException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HibernateLockTest extends BaseTest {

    private CountryDao countryDao;
    private RegionDao regionDao; // Need to pessimisticLock (with out version)
    private Session session = null;
    private Country firstCountry;
    private Country secondCountry;
    private Region firstRegion;
    private Region secondRegion;

    @Before
    public void setUp() {
        countryDao = new CountryDaoImpl(sessionFactory);
        firstCountry = getDefaultCountry();
        Long id = countryDao.create(getDefaultCountry());
        secondCountry = countryDao.read(id);
        firstCountry.setId(id);

        regionDao = new RegionDaoImpl(sessionFactory);
        firstRegion = new Region();
        firstRegion.setCountry(firstCountry);
        firstRegion.setName("Region");
        Long region_id = regionDao.create(firstRegion);
        firstRegion.setId(region_id);
        secondRegion = regionDao.read(region_id);
    }

    private Country getDefaultCountry() {
        Country country = new Country();
        country.setName("Country");
        return country;
    }

    @Test(expected = OptimisticLockException.class)
    public void optimisticEntityLockTest() {

        assertEquals(firstCountry.getName(), secondCountry.getName());

        session = sessionFactory.openSession();
        //Begin transaction
        session.beginTransaction();

        //Update db-row in another thread
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            secondCountry.setName("New name 1");
            countryDao.update(secondCountry);
        });
        future.join();
        //Check that future update db-row
        Country changedCountry = countryDao.read(secondCountry.getId());
        assertEquals("New name 1", changedCountry.getName());
        assertEquals(firstCountry.getVersion() + 1, changedCountry.getVersion());

        firstCountry.setName("New name 2");
        session.update(firstCountry);
        session.flush(); //synhronize(apply) entity with db and throw exception
    }

    @Test(expected = OptimisticEntityLockException.class)
    public void optimisticLockTest() {

        assertEquals(firstCountry.getName(), secondCountry.getName());
        session = sessionFactory.openSession();
        //Begin transaction
        Transaction transaction = session.beginTransaction();
        firstCountry.setName("New name 1");
        session.lock(firstCountry, LockMode.OPTIMISTIC); // if not clearly define, after flush set WRITE mode
        session.update(firstCountry);
        session.flush();
        assertEquals(LockMode.OPTIMISTIC, session.getCurrentLockMode(firstCountry));
        //Update db-row before transaction not commit
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            secondCountry.setName("New name 2");
            countryDao.update(secondCountry);
        });
        future.join();
        //Check that future update db-row
        assertEquals("New name 2", countryDao.read(secondCountry.getId()).getName());
        transaction.commit(); //throw exception
    }

    @Test
    public void pessimisticLockTest() throws InterruptedException {
        assertEquals(firstRegion.getName(), secondRegion.getName());
        session = sessionFactory.openSession();
        //Begin transaction
        Transaction transaction = session.beginTransaction();
        firstRegion.setName("New name Region");
        session.update(firstRegion);
        session.lock(firstRegion, LockMode.PESSIMISTIC_WRITE);
//        session.flush(); it apply query entity and set lock write
        assertEquals(LockMode.PESSIMISTIC_WRITE, session.getCurrentLockMode(firstRegion));
        //Update db-row before transaction not commit
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            secondRegion.setName("New name Region 2");
            regionDao.update(secondRegion);
        });
        TimeUnit.SECONDS.sleep(5);
        assertFalse(future.isDone());
//        Check that future do not update db-row
        assertEquals("Region", regionDao.read(firstRegion.getId()).getName());
        session.getTransaction().commit();
        future.join();
        assertEquals("New name Region 2", regionDao.read(firstRegion.getId()).getName());
    }

    @Test
    public void pessimisticLockJdbcTest() throws InterruptedException {
        assertEquals(firstCountry.getName(), secondCountry.getName());
        session = sessionFactory.openSession();
        //Begin transaction
        Transaction transaction = session.beginTransaction();
        firstCountry.setName("New name Country");
        session.update(firstCountry);
        session.lock(firstCountry, LockMode.PESSIMISTIC_WRITE);
//        session.flush(); //it apply query entity and set lock write
        assertEquals(LockMode.PESSIMISTIC_WRITE, session.getCurrentLockMode(firstCountry));
        //Update db-row before transaction not commit
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            secondCountry.setName("New name Country 2");
            Properties properties = new Properties();
            try {
                InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("db.properties");
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ConnectionFactory connectionFactory = new ConnectionFactoryImpl(properties);
            CountryDao countryDao = new jdbc.CountryDaoImpl(connectionFactory);
            countryDao.update(secondCountry);
        });
        TimeUnit.SECONDS.sleep(5);
        assertFalse(future.isDone());
//        Check that future do not update db-row
        assertEquals("Country", countryDao.read(firstCountry.getId()).getName());
        session.getTransaction().commit();
        future.join();
        assertEquals("New name Country 2", countryDao.read(firstCountry.getId()).getName());
    }

    @After
    public void tearDown() {
        if (session != null) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
                    || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                session.getTransaction().rollback();
            }
            session.close();
        }
        regionDao.delete(regionDao.read(firstRegion.getId()));
        countryDao.delete(countryDao.read(firstCountry.getId()));
    }
}
