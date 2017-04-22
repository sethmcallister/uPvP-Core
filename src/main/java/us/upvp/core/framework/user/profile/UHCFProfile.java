package us.upvp.core.framework.user.profile;

import com.google.common.util.concurrent.AtomicDouble;
import us.upvp.api.framework.user.profile.HCFProfile;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Wout on 14/04/2017.
 */
public class UHCFProfile implements HCFProfile
{
    private AtomicInteger kills;
    private AtomicInteger deaths;
    private AtomicLong currentDeathban;
    private AtomicLong pvpTimer;
    private AtomicBoolean hasJoinedThisMap;
    private AtomicBoolean hasRedeemedRank;
    private AtomicDouble balance;
    private AtomicReference<String> deathMessage;
    private AtomicInteger lives;
    private AtomicReference<UUID> faction;

    public UHCFProfile()
    {
        kills = new AtomicInteger(0);
        deaths = new AtomicInteger(0);
        currentDeathban = new AtomicLong(0);
        pvpTimer = new AtomicLong(0);
        hasJoinedThisMap = new AtomicBoolean(false);
        hasRedeemedRank = new AtomicBoolean(false);
        balance = new AtomicDouble(0);
        deathMessage = new AtomicReference<String>("");
        lives = new AtomicInteger(0);
        faction = new AtomicReference<>(new UUID(0, 0));
    }

    public AtomicInteger getKills()
    {
        return kills;
    }

    public AtomicInteger getDeaths()
    {
        return deaths;
    }

    public AtomicLong getCurrentDeathban()
    {
        return currentDeathban;
    }

    public AtomicLong getPvPTimer()
    {
        return pvpTimer;
    }

    public AtomicBoolean hasJoinedThisMap()
    {
        return hasJoinedThisMap;
    }

    public AtomicBoolean hasRedeemedRank()
    {
        return hasRedeemedRank;
    }

    public AtomicDouble getBalance()
    {
        return balance;
    }

    public AtomicReference<String> getDeathMessage()
    {
        return deathMessage;
    }

    public AtomicInteger getLives()
    {
        return lives;
    }

    public AtomicReference<UUID> getFactionId()
    {
        return faction;
    }

    public void setLives(AtomicInteger lives)
    {
        this.lives = lives;
    }

    public void setDeathMessage(AtomicReference<String> deathMessage)
    {
        this.deathMessage = deathMessage;
    }

    public void setBalance(AtomicDouble balance)
    {
        this.balance = balance;
    }

    public void setCurrentDeathban(AtomicLong currentDeathban)
    {
        this.currentDeathban = currentDeathban;
    }

    public void setDeaths(AtomicInteger deaths)
    {
        this.deaths = deaths;
    }

    public void setKills(AtomicInteger kills)
    {
        this.kills = kills;
    }

    public void setPvpTimer(AtomicLong pvpTimer)
    {
        this.pvpTimer = pvpTimer;
    }

    public void setHasJoinedThisMap(AtomicBoolean hasJoinedThisMap)
    {
        this.hasJoinedThisMap = hasJoinedThisMap;
    }

    public void setHasRedeemedRank(AtomicBoolean hasRedeemedRank)
    {
        this.hasRedeemedRank = hasRedeemedRank;
    }

    public void setFaction(AtomicReference<UUID> faction)
    {
        this.faction = faction;
    }
}
