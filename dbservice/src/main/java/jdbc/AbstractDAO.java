package jdbc;

import dai.DAO;
import dai.Identifiable;
import jdbc.connection.ConnectionFactory;
import jdbc.mapper.ResultSetMapper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractDAO<T extends Identifiable<ID>, ID extends Serializable> implements DAO<T, ID> {

    private final SqlExecutor sqlExecutor = new SqlExecutor();
    protected Pattern pattern = Pattern.compile("[0-9a-zA-Z_]+");
    protected ConnectionFactory connectionFactory;
    protected ResultSetMapper<T> mapper;
    protected List<String> columns;

    public AbstractDAO(ConnectionFactory connectionFactory, ResultSetMapper<T> mapper) {
        this.connectionFactory = connectionFactory;
        this.mapper = mapper;
    }

    protected abstract String getTableName();

    protected abstract String getColumnValue();

    protected abstract String getColumnId();

    protected abstract void fillFieldsForUpdate(PreparedStatement statement, T object) throws SQLException;

    protected abstract void fillFieldsForCreate(PreparedStatement statement, T object) throws SQLException;

    protected String getCreateQuery() {
        checkName(getTableName());
        String createQuery = String.format("INSERT INTO %s (", getTableName());
        if (columns == null || columns.size() == 0) {
            throw new DBException(new Exception("Columns name is not declare"));
        }
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < columns.size(); i++) {
            if (!checkName(columns.get(i))) {
                throw new DBException(new Exception("Column name is wrong: " + columns.get(i)));
            }
            joiner.add(columns.get(i));
        }
        String values = new String(new char[columns.size()])
                .replace("\0", "?,")
                .replaceAll(",$", "");
        createQuery +=  joiner.toString() + ") VALUES (%s)";
        createQuery = String.format(createQuery, values);
        return createQuery;
    }

    protected boolean checkName(String name) {
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    @Override
    public ID create(T object) {
        ID id = null;
        if (object.getId() != null) {
            throw new DBException(new Exception("ID must to be null"));
        }
        String sql = getCreateQuery();
        try (Connection connection = connectionFactory.getConnection()) {
            Object idObject = sqlExecutor.execUpdate(sql, connection, statement -> fillFieldsForCreate(statement, object));
            id = (ID) idObject;
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return id;
    }

    @Override
    public T read(ID id) {
        List<T> list;
        checkName(getTableName());
        checkName(getColumnId());
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", getTableName(), getColumnId());
        try (Connection connection = connectionFactory.getConnection()) {
            list = sqlExecutor.execQuery(sql, connection, mapper, statement -> statement.setObject(1, id));
        } catch (SQLException e) {
            throw new DBException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new DBException(new Exception("Find more than one result"));
        }
        return list.get(0);
    }

    @Override
    public void update(T object) {
        checkName(getTableName());
        checkName(getColumnId());
        checkName(getColumnValue());
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", getTableName(), getColumnValue(), getColumnId());
        try (Connection connection = connectionFactory.getConnection()) {
            sqlExecutor.execUpdate(sql, connection, statement -> fillFieldsForUpdate(statement, object));

        } catch (SQLException ex) {
            throw new DBException(ex);
        }
    }

    @Override
    public void delete(T object) {
        checkName(getTableName());
        checkName(getColumnId());
        String sql = String.format("DELETE FROM %s where %s = ?", getTableName(), getColumnId());
        try (Connection connection = connectionFactory.getConnection()) {
            sqlExecutor.execUpdate(sql, connection, statement -> statement.setObject(1, object.getId()));
        } catch (SQLException ex) {
            throw new DBException(ex);
        }

    }

    @Override
    public List<T> getAll() {
        List<T> list;
        checkName(getTableName());
        String sql = String.format("SELECT * FROM %s", getTableName());
        try (Connection connection = connectionFactory.getConnection()) {
            list = sqlExecutor.execQuery(sql, connection, mapper, null);
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return list;
    }

}
