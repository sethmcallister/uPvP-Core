package us.upvp.core.data.redis;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import us.upvp.api.framework.server.Server;
import us.upvp.core.data.DatabaseManager;
import us.upvp.core.data.model.BanDao;
import us.upvp.core.data.model.MuteDao;
import us.upvp.core.data.model.UserDao;
import us.upvp.core.data.redis.model.RedisBanDao;
import us.upvp.core.data.redis.model.RedisMuteDao;
import us.upvp.core.data.redis.model.RedisUserDao;

import java.util.HashMap;

/**
 * Created by Wout on 14/04/2017.
 */
public class RedisDatabaseManager implements DatabaseManager
{
    private final RedisClient client;
    private final Server server;
    private final BanDao banDao;
    private final MuteDao muteDao;
    private final UserDao userDao;

    public RedisDatabaseManager(Server server)
    {
        HashMap<String, String> databaseOptions = (HashMap<String, String>) server.getConfig().get("database");

        // TODO password
        this.client = RedisClient.create(RedisURI.create(
                "redis://" + databaseOptions.get("hostname") + ":" + String.valueOf(databaseOptions.get("port")) +
                "/0"));
        this.server = server;

        this.banDao = new RedisBanDao(this);
        this.muteDao = new RedisMuteDao(this);
        this.userDao = new RedisUserDao(this);
    }

    public RedisClient getClient()
    {
        return client;
    }

    @Override
    public BanDao getBanDao()
    {
        return banDao;
    }

    @Override
    public MuteDao getMuteDao()
    {
        return muteDao;
    }

    @Override
    public UserDao getUserDao()
    {
        return userDao;
    }

    public Server getServer()
    {
        return server;
    }
}
