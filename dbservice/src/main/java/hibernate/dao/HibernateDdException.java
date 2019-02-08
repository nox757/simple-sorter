package hibernate.dao;

public class HibernateDdException extends RuntimeException {
    public HibernateDdException(Throwable throwable) {
        super(throwable);
    }
}
