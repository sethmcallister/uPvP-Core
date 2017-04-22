package us.upvp.core.data.model;

import java.util.List;

/**
 * Created by Wout on 15/04/2017.
 */
public interface GenericDao<T>
{
    void insert(T object);

    void update(T object);

    void delete(T object);

    T find(Object id);

    List<T> findAll();
}
