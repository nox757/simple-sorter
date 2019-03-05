package jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface FillerStatement {
    void fillStatement(PreparedStatement statement) throws SQLException;
}
