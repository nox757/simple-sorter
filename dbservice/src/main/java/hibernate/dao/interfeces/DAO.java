package hibernate.dao.interfeces;

import java.io.Serializable;
import java.util.List;

/**
 * Base CRUD operation
 * @param <T> type of mapping class object db
 * @param <ID> type of primary key
 */
public interface DAO<T, ID extends Serializable> {

    ID create(T object);

    T read(ID id);

    void update(T object);

    void delete(T object);

    List<T> getAll();
}
