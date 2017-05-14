package net.hcfpvp.core.framework.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.hcfpvp.core.framework.data.model.RedisBanDao;
import net.hcfpvp.core.framework.data.model.RedisMuteDao;
import net.hcfpvp.core.framework.data.model.RedisUserDao;
import net.hcfpvp.core.framework.profile.ProfileHandler;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import net.hcfpvp.api.framework.data.RedisDataManager;
import net.hcfpvp.api.framework.data.messaging.Message;
import net.hcfpvp.api.framework.data.messaging.MessageListener;
import net.hcfpvp.api.framework.server.Server;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class RedisDatabaseManager implements RedisDataManager
{
    private final JedisPool pool;
    private final Server server;
    private final Gson gson;
    private final RedisBanDao banDao;
    private final RedisMuteDao muteDao;
    private final RedisUserDao userDao;

    public RedisDatabaseManager(Server server, ProfileHandler profileHandler)
    {
        HashMap<String, Object> databaseOptions = (HashMap<String, Object>) server.getConfig().get("database");

        this.pool = new JedisPool(new JedisPoolConfig(), (String) databaseOptions.get("hostname"),
                                  (int) databaseOptions.get("port"));

        this.server = server;

        this.banDao = new RedisBanDao(pool);
        this.muteDao = new RedisMuteDao(pool);
        this.userDao = new RedisUserDao(profileHandler, pool);

        this.gson = new GsonBuilder().create();
    }

    @Override
    public JedisPool getJedisPool()
    {
        return pool;
    }

    @Override
    public void registerListener(MessageListener listener)
    {
        try (Jedis jedis = getJedisPool().getResource())
        {
            jedis.subscribe(listener, "update");
        }
    }

    @Override
    public void sendUpdate(String s, UUID uuid)
    {
        try (Jedis jedis = getJedisPool().getResource())
        {
            jedis.publish("update", gson.toJson(new Message(uuid, s)));
        }
    }

    public Server getServer()
    {
        return server;
    }

    public RedisBanDao getBanDao()
    {
        return banDao;
    }

    public RedisMuteDao getMuteDao()
    {
        return muteDao;
    }

    public RedisUserDao getUserDao()
    {
        return userDao;
    }
}
