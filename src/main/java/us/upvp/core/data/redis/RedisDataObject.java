package us.upvp.core.data.redis;

/**
 * Created by Wout on 14/04/2017.
 */
public abstract class RedisDataObject
{
    private RedisPrefix prefix;
    private String key;

    public RedisDataObject(RedisPrefix prefix, String key)
    {
        this.prefix = prefix;
        this.key = key;
    }

    public RedisPrefix getRedisPrefix()
    {
        return prefix;
    }

    public String getRedisKey()
    {
        return key;
    }
}
