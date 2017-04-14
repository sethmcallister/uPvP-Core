package us.upvp.core.framework.server.hcfactions;

import us.upvp.api.framework.server.hcfactions.Claim;
import us.upvp.api.framework.util.Location;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Wout on 14/04/2017.
 */
public class UClaim implements Claim
{
    public AtomicReference<UUID> getWorldUID()
    {
        return null;
    }

    public AtomicInteger getX1()
    {
        return null;
    }

    public AtomicInteger getY1()
    {
        return null;
    }

    public AtomicInteger getZ1()
    {
        return null;
    }

    public AtomicInteger getX2()
    {
        return null;
    }

    public AtomicInteger getY2()
    {
        return null;
    }

    public AtomicInteger getZ2()
    {
        return null;
    }

    public Location getMinimumPoint()
    {
        return null;
    }

    public Location getMaximumPoint()
    {
        return null;
    }

    public AtomicInteger getPrice()
    {
        return null;
    }
}
