package dai;

import java.io.Serializable;

/**
 * Interface to object with identifier
 * @param <ID> type of identifier
 */
public interface Identifiable<ID extends Serializable> {
    ID getId();
}
