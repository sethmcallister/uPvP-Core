package us.upvp.core.data.redis.model;

import us.upvp.core.data.model.BanDao;
import us.upvp.core.data.redis.RedisDatabaseManager;
import us.upvp.core.data.redis.messaging.MessageListener;
import us.upvp.core.data.redis.messaging.MessageType;
import us.upvp.core.framework.ban.UBan;

/**
 * Created by Wout on 15/04/2017.
 */
public class RedisBanDao extends RedisGenericDao<UBan> implements BanDao
{
    public RedisBanDao(RedisDatabaseManager manager)
    {
        super(UBan.class, manager, "id");
    }
}
