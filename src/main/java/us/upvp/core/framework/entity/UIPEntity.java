package us.upvp.core.framework.entity;

import us.upvp.api.framework.entity.EntityId;

/**
 * Created by Wout on 18/04/2017.
 */
public class UIPEntity implements EntityId
{
    private final String ip;

    public UIPEntity(String ip)
    {
        this.ip = ip;
    }

    @Override
    public String getEntityIdentifier()
    {
        return String.format("Type: %s, IP: %s", "IP", ip);
    }

    public String getIp()
    {
        return ip;
    }
}
