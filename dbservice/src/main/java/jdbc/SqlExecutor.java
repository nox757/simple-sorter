package jdbc;

import dai.Identifiable;
import jdbc.mapper.ResultSetMapper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlExecutor {

    public void execUpdate(String sql, Connection connection, FillerStatement fillerStatement) throws SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            fillerStatement.fillStatement(statement);
            int count = statement.executeUpdate();
            if (count != 1) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DBException(new Exception("Try modify more then 1 record : " + count));
            }
        } catch (SQLException ex) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new DBException(ex);
        }
        connection.commit();
        connection.setAutoCommit(true);
    }

    public <T extends Identifiable<? extends Serializable>> List<T> execQuery(String sql, Connection connection, ResultSetMapper<T> mapper, FillerStatement fillerStatement) throws SQLException {
        List<T> list = null;
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if(fillerStatement != null) {
                fillerStatement.fillStatement(statement);
            }
            ResultSet rs = statement.executeQuery();
            list = mapper.toListObjects(rs);
        } catch (SQLException e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new DBException(e);
        }
        connection.commit();
        connection.setAutoCommit(true);
        return list;
    }
}
