package net.hcfpvp.core.framework.data.model;

import net.hcfpvp.core.framework.mute.UMute;
import redis.clients.jedis.JedisPool;

/**
 * Created by Wout on 15/04/2017.
 */
public class RedisMuteDao extends RedisGenericDao<UMute>
{
    public RedisMuteDao(JedisPool pool)
    {
        super(pool, "");
    }

    @Override
    protected String getKey(UMute object)
    {
        return getKeyWithoutIdentifier() + ":" + object.getTarget().getEntityIdentifier();
    }
}
