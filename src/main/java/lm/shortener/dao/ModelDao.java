package lm.shortener.dao;

import java.util.List;

public interface ModelDao<E> {

    E find(String key);
    List<E> findAll();
    boolean create(E element);
    boolean update(E element);
    boolean delete(E element);
}
