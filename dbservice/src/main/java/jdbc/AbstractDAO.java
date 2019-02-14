package jdbc;

import dai.DAO;
import dai.Identifiable;
import jdbc.connection.ConnectionFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

abstract class AbstractDAO<T extends Identifiable<ID>, ID extends Serializable> implements DAO<T, ID> {

    protected ConnectionFactory connectionFactory;

    AbstractDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    protected abstract List<T> parseResultSet(ResultSet rs) throws SQLException;

    protected abstract String getSelectQuery();

    protected abstract String getSelectQueryById();

    protected abstract String getCreateQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

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
            try (PreparedStatement statement = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS)) {
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
        String sql = getSelectQueryById();
        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, id);
                ResultSet rs = statement.executeQuery();
                list = parseResultSet(rs);

            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DBException(e);
            }
            connection.commit();
            connection.setAutoCommit(true);
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
        String sql = getUpdateQuery();
        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                fillFieldsForUpdate(statement, object);
                int count = statement.executeUpdate();
                if (count != 1) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    throw new DBException(new Exception("Update try modify more then 1 record: " + count));
                }
            } catch (SQLException ex) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DBException(ex);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
    }

    @Override
    public void delete(T object) {
        String sql = getDeleteQuery();
        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try {
                    statement.setObject(1, object.getId());
                } catch (SQLException ex) {
                    throw new DBException(ex);
                }
                int count = statement.executeUpdate();
                if (count != 1) {
                    throw new DBException(new Exception("Try delete more then 1 record: " + count));
                }
            } catch (SQLException ex) {
                throw new DBException(ex);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            throw new DBException(ex);
        }

    }

    @Override
    public List<T> getAll() {
        List<T> list;
        String sql = getSelectQuery();
        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet rs = statement.executeQuery();
                list = parseResultSet(rs);

            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DBException(e);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return list;
    }
}
