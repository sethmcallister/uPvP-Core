package us.upvp.core.framework.data.model;

import us.upvp.core.framework.ban.UBan;
import us.upvp.core.framework.data.RedisDatabaseManager;

/**
 * Created by Wout on 15/04/2017.
 */
public class RedisBanDao extends RedisGenericDao<UBan>
{
    public RedisBanDao(RedisDatabaseManager manager)
    {
        super(UBan.class, manager, "id");
    }
}
