package us.upvp.core.framework.server.hcfactions;

import com.google.common.util.concurrent.AtomicDouble;
import us.upvp.api.framework.server.hcfactions.Claim;
import us.upvp.api.framework.server.hcfactions.Faction;
import us.upvp.api.framework.util.Location;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Created by Wout on 14/04/2017.
 */
public class UFaction implements Faction
{
    public UUID getFactionId()
    {
        return null;
    }

    public AtomicReference<String> getFactionName()
    {
        return null;
    }

    public AtomicReferenceArray<UUID> getAllMembers()
    {
        return null;
    }

    public AtomicReferenceArray<UUID> getMembers()
    {
        return null;
    }

    public AtomicReferenceArray<UUID> getCaptains()
    {
        return null;
    }

    public AtomicReferenceArray<UUID> getAllies()
    {
        return null;
    }

    public AtomicReference<UUID> getLeader()
    {
        return null;
    }

    public AtomicDouble getBalance()
    {
        return null;
    }

    public AtomicDouble getDTR()
    {
        return null;
    }

    public AtomicDouble getMaxDTR()
    {
        return null;
    }

    public Location getHome()
    {
        return null;
    }

    public AtomicLong getDTRFreeze()
    {
        return null;
    }

    public String getFactionInformation()
    {
        return null;
    }

    public AtomicReference<Claim> getFactionClaim()
    {
        return null;
    }
}
