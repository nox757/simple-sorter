package hibernate.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO <T, P extends Serializable> {

    P create(T object);

    T read(P id);

    void update(T object);

    void delete(T object);

}
