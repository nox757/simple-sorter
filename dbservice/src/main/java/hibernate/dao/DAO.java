package hibernate.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO <T, ID extends Serializable> {

    ID create(T object);

    T read(ID id);

    void update(T object);

    void delete(T object);

    List<T> getAll();
}
