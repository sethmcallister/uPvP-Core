package us.upvp.core.data.redis;

/**
 * Created by Wout on 14/04/2017.
 */
public enum RedisPrefix
{
    PRODUCTION_PLAYER("production:player:"),
    DEVELOPMENT_PLAYER("development:player:"),
    TEST_PLAYER("test:player:");

    private String prefix;

    RedisPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getPrefix()
    {
        return prefix;
    }
}
