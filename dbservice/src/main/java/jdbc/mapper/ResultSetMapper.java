package jdbc.mapper;

import dai.Identifiable;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultSetMapper<T extends Identifiable<? extends Serializable>> {

    List<T> toListObjects(ResultSet resultSet) throws SQLException;
}
