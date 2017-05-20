package net.hcfpvp.core.framework.data.model;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.data.model.GenericDao;
import net.hcfpvp.api.framework.entity.EntityId;
import net.hcfpvp.api.framework.permission.Rank;
import net.hcfpvp.core.framework.data.adapter.EntityIdAdapter;
import net.hcfpvp.core.framework.data.adapter.RankAdapter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Wout on 15/04/2017.
 */
public abstract class RedisGenericDao<T> implements GenericDao<T>
{
    private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {};
    private final Type type = typeToken.getType();
    private final JedisPool pool;
    private final String identifier;
    private Gson gson;

    public RedisGenericDao(JedisPool pool, String identifier)
    {
        this.identifier = identifier;
        this.pool = pool;
        this.gson = new GsonBuilder().serializeNulls()
                                     .registerTypeHierarchyAdapter(EntityId.class, new EntityIdAdapter())
                                     .registerTypeAdapter(Rank.class, new RankAdapter())
                                     .create();
    }

    @Override
    public void insert(T object)
    {
        try (Jedis connection = getConnection())
        {
            connection.set(getKey(object), gson.toJson(object, type));
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
        try (Jedis connection = getConnection())
        {
            connection.del(getKey(object));
        }
    }

    @Override
    public T find(Object id)
    {
        try (Jedis connection = getConnection())
        {
            return gson.fromJson(connection.get(getKeyWithoutIdentifier() + ":" + id.toString()),
                                 (Class<T>) type);
        }
    }

    @Override
    public List<T> findAll()
    {
        try (Jedis connection = getConnection())
        {
            Set<String> keys = connection.keys(getKeyWithoutIdentifier() + ":*");

            return keys.stream()
                       .map(k -> (T) gson.fromJson(connection.get(k), type))
                       .collect(Collectors.toList());
        }
    }

    protected String getKey(T object)
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
        catch (NoSuchFieldException | IllegalAccessException ignored)
        {

        }

        return getKeyWithoutIdentifier() + ":" + id.toString();
    }

    protected String getKeyWithoutIdentifier()
    {
        return API.getServer().getEnvironment().name().toLowerCase() + ":" +
               typeToken.getRawType()
                        .getSimpleName()
                        .toLowerCase()
                        .substring(1, typeToken.getRawType().getSimpleName().length());
    }

    public Jedis getConnection()
    {
        return pool.getResource();
    }

    public Gson getGson()
    {
        return gson;
    }
}
