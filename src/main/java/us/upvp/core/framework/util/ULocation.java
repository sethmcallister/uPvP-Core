package us.upvp.core.framework.util;

import us.upvp.api.framework.util.Location;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Wout on 14/04/2017.
 */
public class ULocation implements Location
{
    private final AtomicReference<UUID> worldId;
    private final AtomicInteger x;
    private final AtomicInteger y;
    private final AtomicInteger z;

    public ULocation(AtomicReference<UUID> worldId, AtomicInteger x, AtomicInteger y, AtomicInteger z)
    {
        this.worldId = worldId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AtomicReference<UUID> getWorldId()
    {
        return null;
    }

    public AtomicInteger getX()
    {
        return null;
    }

    public AtomicInteger getY()
    {
        return null;
    }

    public AtomicInteger getZ()
    {
        return null;
    }
}
