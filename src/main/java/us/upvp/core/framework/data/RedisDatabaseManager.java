package us.upvp.core.framework.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.pubsub.StatefulRedisPubSubConnection;
import us.upvp.api.framework.data.RedisDataManager;
import us.upvp.api.framework.data.messaging.Message;
import us.upvp.api.framework.data.messaging.MessageListener;
import us.upvp.api.framework.server.Server;
import us.upvp.core.framework.data.model.RedisBanDao;
import us.upvp.core.framework.data.model.RedisMuteDao;
import us.upvp.core.framework.data.model.RedisUserDao;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class RedisDatabaseManager implements RedisDataManager
{
    private final RedisClient client;
    private final Server server;
    private final Gson gson;
    private final RedisBanDao banDao;
    private final RedisMuteDao muteDao;
    private final RedisUserDao userDao;

    public RedisDatabaseManager(Server server)
    {
        HashMap<String, String> databaseOptions = (HashMap<String, String>) server.getConfig().get("database");

        this.client = RedisClient.create(RedisURI.create(
                "redis://" + databaseOptions.get("hostname") + ":" + String.valueOf(databaseOptions.get("port")) +
                "/0"));

        this.server = server;

        StatefulRedisPubSubConnection<String, String> conn = getClient().connectPubSub();

        this.banDao = new RedisBanDao(this);
        this.muteDao = new RedisMuteDao(this);
        this.userDao = new RedisUserDao(this, conn);

        conn.sync().subscribe("update");

        this.gson = new GsonBuilder().create();
    }

    public RedisClient getClient()
    {
        return client;
    }

    @Override
    public void registerListener(MessageListener listener)
    {
        StatefulRedisPubSubConnection<String, String> conn = getClient().connectPubSub();
        conn.addListener(listener);
        conn.sync().subscribe("update");
    }

    @Override
    public void sendUpdate(String s, UUID uuid)
    {
        try (StatefulRedisPubSubConnection<String, String> conn = getClient().connectPubSub())
        {
            conn.sync().publish("update", gson.toJson(new Message(uuid, s)));
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
