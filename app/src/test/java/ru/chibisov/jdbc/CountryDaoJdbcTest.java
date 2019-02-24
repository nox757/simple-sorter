package ru.chibisov.jdbc;

import dai.CountryDao;
import entities.Country;
import jdbc.CountryDaoImpl;
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

public class CountryDaoJdbcTest extends BaseJdbcTest {

    private final static String QUERY = "select * from country";
    private CountryDao countryDao;
    private Country country;

    @Before
    public void setUp() {
        countryDao = new CountryDaoImpl(connectionFactory);
        country = new Country();
        country.setName("Country");
        country.setId(countryDao.create(country));
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
                        (country1.getId().equals(countryInserting.getId()))))
                .collect(Collectors.toList());
        assertNotNull(foundCountries);
        assertThat(foundCountries.size(), is(2));
        assertThat(foundCountries, hasItem(country));
        assertThat(foundCountries, hasItem(countryInserting));
    }

    @After
    public void tearDown() {//TODO: сделать нормальную очистку данных

        List<Country> countries = countryDao.getAll();
        for (Country entity : countries) {
            countryDao.delete(entity);
        }
    }

}
