package lm.shortener.dao;

public interface ModelDao<E> {

    E find(String key);
    boolean create(E element);
    boolean update(E element);
    boolean delete(E element);
}
