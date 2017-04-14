package us.upvp.core.data.redis;

import us.upvp.core.data.DataDriver;

import java.util.List;

/**
 * Created by Wout on 14/04/2017.
 */
public class RedisDataDriver<T extends RedisDataObject> implements DataDriver<T>
{
    public RedisDataDriver()
    {

    }

    public T insert(T object)
    {
        return null;
    }

    public T update(T object)
    {
        return null;
    }

    public T delete(T object)
    {
        return null;
    }

    public T find(String id)
    {
        return null;
    }

    public List<T> findAll()
    {
        return null;
    }
}
