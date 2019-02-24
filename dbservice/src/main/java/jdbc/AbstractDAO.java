package jdbc;

import dai.DAO;
import dai.Identifiable;
import jdbc.connection.ConnectionFactory;
import jdbc.mapper.ResultSetMapper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractDAO<T extends Identifiable<ID>, ID extends Serializable> implements DAO<T, ID> {

    protected ConnectionFactory connectionFactory;

    protected ResultSetMapper<T> mapper;

    private final SqlExecutor sqlExecutor = new SqlExecutor();

    public AbstractDAO(ConnectionFactory connectionFactory, ResultSetMapper<T> mapper) {
        this.connectionFactory = connectionFactory;
        this.mapper = mapper;

    }

    protected abstract String getTableName();

    protected abstract String getColumnValue();

    protected abstract String getColumnId();


    protected abstract String getCreateQuery();


    protected abstract void fillFieldsForUpdate(PreparedStatement statement, T object) throws SQLException;

    protected abstract void fillFieldsForCreate(PreparedStatement statement, T object) throws SQLException;

    @Override
    public ID create(T object) {
        ID id = null;
        if (object.getId() != null) {
            throw new DBException(new Exception("ID must to be null"));
        }
        String sql = getCreateQuery();
        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                fillFieldsForCreate(statement, object);
                int count = statement.executeUpdate();
                if (count != 1) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    throw new DBException(new Exception("On persist modify more then 1 record: " + count));
                }
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = (ID) rs.getObject(1);
                }
            } catch (SQLException ex) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DBException(ex);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return id;
    }

    @Override
    public T read(ID id) {
        List<T> list;
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", getTableName(), getColumnId());
        try (Connection connection = connectionFactory.getConnection()) {
            list = sqlExecutor.execQuery(sql, connection, mapper, new FillerStatement() {
                @Override
                public void fillStatement(PreparedStatement statement) throws SQLException {
                    statement.setObject(1, id);
                }
            });
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
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", getTableName(), getColumnValue(), getColumnId());
        try (Connection connection = connectionFactory.getConnection()) {
            sqlExecutor.execUpdate(sql, connection, new FillerStatement() {
                @Override
                public void fillStatement(PreparedStatement statement) throws SQLException {
                    fillFieldsForUpdate(statement, object);
                }
            });

        } catch (SQLException ex) {
            throw new DBException(ex);
        }
    }

    @Override
    public void delete(T object) {
        String sql = String.format("DELETE FROM %s where %s = ?", getTableName(), getColumnId());
        try (Connection connection = connectionFactory.getConnection()) {
            sqlExecutor.execUpdate(sql, connection, new FillerStatement() {
                @Override
                public void fillStatement(PreparedStatement statement) throws SQLException {
                    statement.setObject(1, object.getId());
                }
            });
        } catch (SQLException ex) {
            throw new DBException(ex);
        }

    }

    @Override
    public List<T> getAll() {
        List<T> list;
        String sql = String.format("SELECT * FROM %s", getTableName());
        try (Connection connection = connectionFactory.getConnection()) {
            list = sqlExecutor.execQuery(sql, connection, mapper, null);
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return list;
    }

}
