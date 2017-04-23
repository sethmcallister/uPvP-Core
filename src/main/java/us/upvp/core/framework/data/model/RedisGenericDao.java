package us.upvp.core.framework.data.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import us.upvp.api.framework.data.model.GenericDao;
import us.upvp.core.framework.data.RedisDatabaseManager;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wout on 15/04/2017.
 */
public abstract class RedisGenericDao<T> implements GenericDao<T>
{
    private final Class<T> clazz;
    private final RedisDatabaseManager manager;
    private final String identifier;
    private Gson gson;

    public RedisGenericDao(Class<T> clazz, RedisDatabaseManager manager, String identifier)
    {
        this.clazz = clazz;
        this.manager = manager;
        this.identifier = identifier;
        this.gson = new GsonBuilder().serializeNulls().create();
    }

    @Override
    public void insert(T object)
    {
        try (StatefulRedisConnection<String, String> connection = getConnection())
        {
            connection.sync().set(getKey(object), gson.toJson(object, clazz));
        }
    }

    @Override
    public void update(T object)
    {
        insert(object);
    }

    @Override
    public void delete(T object)
    {
        try (StatefulRedisConnection<String, String> connection = getConnection())
        {
            connection.sync().del(getKey(object));
        }
    }

    @Override
    public T find(Object id)
    {
        try (StatefulRedisConnection<String, String> connection = getConnection())
        {
            return gson.fromJson(connection.sync().get(getKeyWithoutIdentifier() + ":" + id.toString()), clazz);
        }
    }

    @Override
    public List<T> findAll()
    {
        try (StatefulRedisConnection<String, String> connection = getConnection())
        {
            List<String> keys = connection.sync().keys(getKeyWithoutIdentifier());

            return keys.stream()
                       .map(k -> (T) gson.fromJson(connection.sync().get(k), clazz))
                       .collect(Collectors.toList());
        }
    }

    public StatefulRedisConnection<String, String> getConnection()
    {
        return manager.getClient().connect();
    }

    private final String getKey(T object)
    {
        Object id = "";
        try
        {
            Field idField = null;

            try
            {
                idField = object.getClass().getDeclaredField(identifier);
            }
            catch (NoSuchFieldException ex)
            {
                // ignored
            }

            if (idField == null)
            {
                idField = object.getClass().getSuperclass().getDeclaredField(identifier);
            }

            idField.setAccessible(true);
            id = idField.get(object);
            idField.setAccessible(false);
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            // TODO handle this?
        }

        return getKeyWithoutIdentifier() + ":" + id.toString();
    }

    private String getKeyWithoutIdentifier()
    {
        return manager.getServer().getEnvironment().name().toLowerCase() + ":" +
               clazz.getSimpleName().toLowerCase().substring(1, clazz.getSimpleName().length());
    }
}
