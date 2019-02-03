import hibernate.dao.CountryDaoImpl;
import hibernate.entities.Country;
import services.CountryServices;

public class Main {

    public static void main(String[] args) {
        CountryServices countryServices = new CountryServices(new CountryDaoImpl());
        countryServices.save(new Country().setName("55mmmmmm"));
        System.out.println(countryServices.getById(5L).getName());
    }
}
