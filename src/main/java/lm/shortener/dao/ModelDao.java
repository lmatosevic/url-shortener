package lm.shortener.dao;

import java.util.List;

/**
 * Interface which provides all operations for manipulation with java objects and stored data.
 *
 * @param <E> Type of model object used in implementation.
 *
 * @author Luka
 */
public interface ModelDao<E> {

    /**
     * Finds one object based on provided key. Key must be unique value.
     *
     * @param key Unique value that identifies object.
     * @return Object model or null if not found in storage.
     */
    E find(String key);

    /**
     * Finds all objects from storage.
     *
     * @return All objects.
     */
    List<E> findAll();

    /**
     * Creates new data in storage from provided element.
     *
     * @param element Object to store.
     * @return True on success, false on failure.
     */
    boolean create(E element);

    /**
     * Updates stored data for provided element.
     *
     * @param element Object which contains new data. Key must be the same as stored object to succede.
     * @return True on success, false on failure.
     */
    boolean update(E element);

    /**
     * Deletes object from storage.
     *
     * @param element Object to delete from storage.
     * @return True on success, false otherwise.
     */
    boolean delete(E element);
}
