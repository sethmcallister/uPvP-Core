package us.upvp.core.framework.data.model;

import us.upvp.core.framework.data.RedisDatabaseManager;
import us.upvp.core.framework.mute.UMute;

/**
 * Created by Wout on 15/04/2017.
 */
public class RedisMuteDao extends RedisGenericDao<UMute>
{
    public RedisMuteDao(RedisDatabaseManager manager)
    {
        super(UMute.class, manager, "id");
    }
}
