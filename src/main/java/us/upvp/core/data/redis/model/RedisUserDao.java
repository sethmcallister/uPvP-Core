package us.upvp.core.data.redis.model;

import com.lambdaworks.redis.pubsub.StatefulRedisPubSubConnection;
import us.upvp.api.API;
import us.upvp.api.framework.user.User;
import us.upvp.core.data.model.UserDao;
import us.upvp.core.data.redis.RedisDatabaseManager;
import us.upvp.core.data.redis.messaging.MessageListener;
import us.upvp.core.data.redis.messaging.MessageType;
import us.upvp.core.framework.user.UOfflineUser;
import us.upvp.core.framework.user.UUserManager;

/**
 * Created by Wout on 15/04/2017.
 */
public class RedisUserDao extends RedisGenericDao<UOfflineUser> implements UserDao
{
    public RedisUserDao(RedisDatabaseManager manager)
    {
        super(UOfflineUser.class, manager, "uniqueId");

        StatefulRedisPubSubConnection<String, String> connection = manager.getClient().connectPubSub();

        connection.addListener(new MessageListener(MessageType.USER, (msg) ->
        {
            UUserManager mng = (UUserManager) API.getUserManager();

            for (int i = 0; i < mng.getUsers().size(); i++)
            {
                User u = mng.getUsers().get(i);

                if (u.getUniqueId().equals(msg.getId()))
                {
                    mng.getUsers().set(i, mng.convert(mng.getDao().find(msg.getId())));
                }
            }
        }));

        connection.sync().subscribe("update");
    }
}
