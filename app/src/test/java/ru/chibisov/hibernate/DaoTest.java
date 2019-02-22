package ru.chibisov.hibernate;

import dai.CountryDao;
import dai.DAO;
import dai.Identifiable;
import dai.RegionDao;
import entities.Country;
import entities.Region;
import hibernate.dao.CountryDaoImpl;
import hibernate.dao.RegionDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class DaoTest extends BaseTest {

    private CountryDao countryDao;
    private RegionDao regionDao;
    private Country country;
    private Region region;

    @Before
    public void setUp() {
        countryDao = new CountryDaoImpl(sessionFactory);
        country = new Country();
        country.setName("Country");
        country.setId(countryDao.create(country));

        regionDao = new RegionDaoImpl(sessionFactory);
        region = new Region();
        region.setCountry(country);
        region.setName("Region");
        region.setId(regionDao.create(region));
    }

    @Test
    public void readCountryTest() {
        Country country = this.country;

        Country foundCountry = countryDao.read(country.getId());
        assertNotNull(foundCountry);
        assertEquals(country.getVersion(), foundCountry.getVersion());
        assertEquals(country.getName(), foundCountry.getName());
    }

    @Test
    public void saveAndGetAllCountryTest() {
        Country country = this.country;
        Country countryInserting = new Country();
        countryInserting.setName("New name");
        countryInserting.setId(countryDao.create(countryInserting));

        List<Country> foundCountries = countryDao.getAll().stream()
                .filter(country1 -> (country1.getId().equals(country.getId()) ||
                        (country1.getId().equals(country.getId()))))
                .collect(Collectors.toList());
        assertNotNull(foundCountries);
        assertThat(foundCountries.size(), is(2));
        assertThat(foundCountries, hasItem(country));
        assertThat(foundCountries, hasItem(countryInserting));

    }

    @Test
    public void getRegionFromCountryTest() {
        Country country = countryDao.read(this.country.getId());
        Region region = this.region;

        List<Region> regions = country.getRegions();
        regions.size();
        assertNotNull(regions);
        assertThat(regions.size(), is(1));
        assertThat(regions, hasItem(region));

    }

    @After
    public void tearDown() {//TODO: сделать нормальную очистку данных
        List<Region> regions = regionDao.getAll();
        clearTable(regions, regionDao);

        List<Country> countries = countryDao.getAll();
        clearTable(countries, countryDao);
    }

    private <T extends Identifiable> void clearTable(List<T> list, DAO dao) {
        for (T entity : list) {
            dao.delete(entity);
        }
    }

}
