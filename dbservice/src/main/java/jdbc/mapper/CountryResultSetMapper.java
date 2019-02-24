package jdbc.mapper;

import entities.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryResultSetMapper implements ResultSetMapper<Country> {

    @Override
    public List<Country> toListObjects(ResultSet result) throws SQLException {
        List<Country> listCountry = new ArrayList<>();
        while (result.next()) {
            Country country = new Country();
            country.setId(result.getLong("country_id"));
            country.setName(result.getString("name"));
            listCountry.add(country);
        }
        return listCountry;
    }
}
