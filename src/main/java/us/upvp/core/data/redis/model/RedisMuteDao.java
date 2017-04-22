package us.upvp.core.data.redis.model;

import us.upvp.core.data.model.MuteDao;
import us.upvp.core.data.redis.RedisDatabaseManager;
import us.upvp.core.framework.mute.UMute;

/**
 * Created by Wout on 15/04/2017.
 */
public class RedisMuteDao extends RedisGenericDao<UMute> implements MuteDao
{
    public RedisMuteDao(RedisDatabaseManager manager)
    {
        super(UMute.class, manager, "id");
    }
}
