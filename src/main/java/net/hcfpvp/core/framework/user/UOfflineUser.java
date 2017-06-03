package net.hcfpvp.core.framework.user;

import com.google.common.collect.Maps;
import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.entity.IPEntity;
import net.hcfpvp.api.framework.permission.Group;
import net.hcfpvp.api.framework.permission.Rank;
import net.hcfpvp.api.framework.user.OfflineUser;
import net.hcfpvp.api.profiles.Profile;
import net.hcfpvp.api.profiles.core.StandardProfileKey;
import net.hcfpvp.core.framework.permission.URank;

import java.util.*;

/**
 * Created by Wout on 14/04/2017.
 */
public class UOfflineUser implements OfflineUser, Cloneable
{
    private final UUID uniqueId;
    private final HashMap<String, Profile> profiles;

    public UOfflineUser(UUID uniqueId)
    {
        this.uniqueId = uniqueId;
        this.profiles = Maps.newHashMap();
    }

    public UOfflineUser(UUID uniqueId, Collection<Profile> profiles)
    {
        this.uniqueId = uniqueId;
        this.profiles = Maps.newHashMap();

        profiles.forEach(p -> this.profiles.put(p.getProfileName(), p));
    }

    @Override
    public UUID getUniqueId()
    {
        return uniqueId;
    }

    @Override
    public Profile getStandardProfile()
    {
        return profiles.getOrDefault("standard", null);
    }

    @Override
    public Profile getProfile(String s)
    {
        return profiles.getOrDefault(s, null);
    }

    @Override
    public String getLastKnownUsername()
    {
        return getStandardProfile().getStringValue(StandardProfileKey.LAST_KNOWN_USERNAME.getKey());
    }

    @Override
    public Set<Rank> getRanks()
    {
        return (Set<Rank>) getStandardProfile().getObjectValue(StandardProfileKey.RANKS.getKey());
    }

    @Override
    public Rank getRank()
    {
        return getRanks().stream()
                         .map(r -> (URank) r)
                         .filter(r -> API.getServer().getName().equalsIgnoreCase(r.getApplicableServer()))
                         .findAny()
                         .orElse(getRanks().stream()
                                           .map(r -> (URank) r)
                                           .filter(r -> r.getApplicableServer().equals("___GLOBAL___"))
                                           .findAny()
                                           .orElse(null));
    }

    @Override
    public void setRank(Group group, String s)
    {
        URank r = (URank) getRanks().stream()
                                    .filter(ra -> ra.getApplicableServer().equalsIgnoreCase(s))
                                    .findAny()
                                    .orElse(null);

        if (r != null)
        {
            r.setGroup(group);
        }
        else
        {
            getRanks().add(new URank(group, s));
        }
    }

    @Override
    public Set<IPEntity> getIPs()
    {
        return (Set<IPEntity>) getStandardProfile().getObjectValue(StandardProfileKey.IPS.getKey());
    }

    @Override
    public long getJoinDate()
    {
        return getStandardProfile().getLongValue(StandardProfileKey.JOIN_DATE.getKey());
    }

    @Override
    public long getLastSeenDate()
    {
        return getStandardProfile().getLongValue(StandardProfileKey.LAST_SEEN.getKey());
    }

    @Override
    public IPEntity getMostRecentIP()
    {
        return (IPEntity) getStandardProfile().getEntityValue(StandardProfileKey.MOST_RECENT_IP.getKey());
    }

    @Override
    public boolean isOnline()
    {
        return API.getUserManager().findByUniqueId(uniqueId) != null;
    }

    @Override
    public void update()
    {
        UUserManager manager = (UUserManager) API.getUserManager();

        manager.getDao().update(this);

        API.getRedisDataManager().sendUpdate("user", getUniqueId());
    }

    @Override
    public String getEntityIdentifier()
    {
        return String.format("USER|%s", getUniqueId().toString());
    }

    public HashMap<String, Profile> getProfiles()
    {
        return profiles;
    }
}
