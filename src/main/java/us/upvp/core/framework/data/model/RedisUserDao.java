package us.upvp.core.framework.data.model;

import com.lambdaworks.redis.pubsub.StatefulRedisPubSubConnection;
import us.upvp.api.API;
import us.upvp.api.framework.data.messaging.MessageListener;
import us.upvp.api.framework.user.User;
import us.upvp.core.framework.data.RedisDatabaseManager;
import us.upvp.core.framework.user.UOfflineUser;
import us.upvp.core.framework.user.UUserManager;

/**
 * Created by Wout on 15/04/2017.
 */
public class RedisUserDao extends RedisGenericDao<UOfflineUser>
{
    public RedisUserDao(RedisDatabaseManager manager, StatefulRedisPubSubConnection<String, String> conn)
    {
        super(UOfflineUser.class, manager, "uniqueId");

        conn.addListener(new MessageListener("user", (msg) ->
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
    }
}
