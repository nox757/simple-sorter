import hibernate.dao.CountryDaoImpl;
import hibernate.entities.Country;
import services.CountryServices;

public class Main {

    public static void main(String[] args) {
        CountryServices countryServices = new CountryServices(new CountryDaoImpl());
        Country country = new Country();
        country.setName("jjjjjjj2");

        countryServices.save(country);
        System.out.println(countryServices.getById(2L).getName());
    }
}
