import hibernate.dao.CountryDaoImpl;
import services.CountryServices;

public class Main {

    public static void main(String[] args) {
        CountryServices countryServices = new CountryServices(new CountryDaoImpl());
//        countryServices.saveCountry(new Country().setId(2L).setName("mmmmmm"));
        System.out.println(countryServices.getById(2L).getName());
    }
}
