package us.upvp.core.data;

import java.util.List;

/**
 * Created by Wout on 14/04/2017.
 */
public interface DataDriver<T>
{
    T insert(T object);
    T update(T object);
    T delete(T object);
    T find(String id);
    List<T> findAll();
}
