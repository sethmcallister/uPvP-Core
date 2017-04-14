package us.upvp.core.framework.user.profile;

import com.google.common.util.concurrent.AtomicDouble;
import us.upvp.api.framework.server.hcfactions.Faction;
import us.upvp.api.framework.user.profile.HCFProfile;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Wout on 14/04/2017.
 */
public class UHCFProfile implements HCFProfile
{
    public AtomicInteger getKills()
    {
        return null;
    }

    public AtomicInteger getDeaths()
    {
        return null;
    }

    public AtomicLong getCurrentDeathban()
    {
        return null;
    }

    public AtomicLong getPvPTimer()
    {
        return null;
    }

    public AtomicBoolean hasJoinedThisMap()
    {
        return null;
    }

    public AtomicBoolean hasRedeemedRank()
    {
        return null;
    }

    public AtomicDouble getBalance()
    {
        return null;
    }

    public AtomicReference<String> getDeathMessage()
    {
        return null;
    }

    public AtomicInteger getLives()
    {
        return null;
    }

    public AtomicReference<Faction> getFaction()
    {
        return null;
    }
}
