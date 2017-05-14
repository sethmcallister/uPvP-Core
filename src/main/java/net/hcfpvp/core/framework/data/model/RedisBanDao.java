package net.hcfpvp.core.framework.data.model;

import redis.clients.jedis.JedisPool;
import net.hcfpvp.core.framework.ban.UBan;

/**
 * Created by Wout on 15/04/2017.
 */
public class RedisBanDao extends RedisGenericDao<UBan>
{
    public RedisBanDao(JedisPool pool)
    {
        super(pool, "");
    }

    @Override
    protected String getKey(UBan object)
    {
        return getKeyWithoutIdentifier() + ":" + object.getTarget().getEntityIdentifier();
    }
}
