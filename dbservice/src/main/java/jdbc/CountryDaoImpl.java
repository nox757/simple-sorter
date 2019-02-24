package jdbc;

import dai.CountryDao;
import entities.Country;
import jdbc.connection.ConnectionFactory;
import jdbc.mapper.CountryResultSetMapper;
import jdbc.mapper.ResultSetMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountryDaoImpl extends AbstractDAO<Country, Long> implements CountryDao {

    private final static String TABLE_NAME = "country";
    private final static String COLUMN_ID = "country_id";
    private final static String COLUMN_VALUE = "name";

    public String getTableName() {
        return TABLE_NAME;
    }

    public String getColumnId() {
        return COLUMN_ID;
    }

    public String getColumnValue() {
        return COLUMN_VALUE;
    }

    private final static String CREATE_QUERY = "INSERT INTO country (name) VALUES(?)";
    private final static ResultSetMapper<Country> resultSetMapper = new CountryResultSetMapper();

    public CountryDaoImpl(ConnectionFactory connectionFactory) {
        super(connectionFactory, resultSetMapper);
    }



    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
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
