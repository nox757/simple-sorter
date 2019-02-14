package jdbc;

import dai.CountryDao;
import entities.Country;
import jdbc.connection.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl extends AbstractDAO<Country, Long> implements CountryDao {

    private final static String SELECT_QUERY = "SELECT * FROM country";
    private final static String SELECT_QUERY_BY_ID = "SELECT * FROM country WHERE country_id = ?";
    private final static String CREATE_QUERY = "INSERT INTO country (name) VALUES(?)";
    private final static String UPDATE_QUERY = "UPDATE country SET name = ? WHERE country_id = ?";
    private final static String DELETE_QUERY = "DELETE FROM country where country_id = ?";

    public CountryDaoImpl(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected List<Country> parseResultSet(ResultSet result) throws SQLException {
        List<Country> listStudent = new ArrayList<>();
        while (result.next()) {
            Country country = new Country();
            country.setId(result.getLong("country_id"));
            country.setName(result.getString("name"));
            listStudent.add(country);
        }
        return listStudent;
    }

    @Override
    protected String getSelectQuery() {
        return SELECT_QUERY;
    }

    @Override
    protected String getSelectQueryById() {
        return SELECT_QUERY_BY_ID;
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected void fillFieldsForUpdate(PreparedStatement statement, Country country) throws SQLException {
        statement.setString(1, country.getName());
        statement.setLong(2, country.getId());
    }

    @Override
    protected void fillFieldsForCreate(PreparedStatement statement, Country country) throws SQLException {
        statement.setString(1, country.getName());
    }
}
