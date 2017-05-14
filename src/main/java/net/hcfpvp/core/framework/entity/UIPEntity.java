package net.hcfpvp.core.framework.entity;

import net.hcfpvp.api.framework.entity.IPEntity;

/**
 * Created by Wout on 18/04/2017.
 */
public class UIPEntity implements IPEntity
{
    private final String ip;

    public UIPEntity(String ip)
    {
        this.ip = ip;
    }

    @Override
    public String getEntityIdentifier()
    {
        return String.format("IP|%s", ip);
    }

    @Override
    public boolean equals(Object obj)
    {
        return getIP().equalsIgnoreCase(((UIPEntity) obj).getIP());
    }

    public String getIP()
    {
        return ip;
    }
}
