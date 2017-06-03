package net.hcfpvp.core.framework.user;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.ban.Ban;
import net.hcfpvp.api.framework.module.event.events.UserConnectedEvent;
import net.hcfpvp.api.framework.module.event.events.UserDisconnectedEvent;
import net.hcfpvp.api.framework.user.OfflineUser;
import net.hcfpvp.api.framework.user.User;
import net.hcfpvp.api.framework.user.UserManager;
import net.hcfpvp.api.profiles.core.StandardProfileKey;
import net.hcfpvp.core.framework.data.RedisDatabaseManager;
import net.hcfpvp.core.framework.data.model.RedisUserDao;
import net.hcfpvp.core.framework.util.UUIDFetcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class UUserManager implements UserManager
{
    private final RedisUserDao dao;
    private final List<User> users;

    public UUserManager(RedisDatabaseManager manager)
    {
        this.dao = manager.getUserDao();
        this.users = Lists.newArrayList();
    }

    public User findByUniqueId(UUID uuid)
    {
        return users.stream().filter(u -> u.getUniqueId().equals(uuid)).findFirst().orElse(null);
    }

    public ImmutableList<User> findAllUsers()
    {
        return ImmutableList.copyOf(users);
    }

    public OfflineUser findOfflineByUniqueId(UUID uuid)
    {
        return dao.find(uuid);
    }

    public List<OfflineUser> findAllUsersAnyway()
    {
        return new ArrayList<>(dao.findAll());
    }

    public OfflineUser findOfflineByName(String s)
    {
        try
        {
            return findOfflineByUniqueId(new UUIDFetcher(Collections.singletonList(s)).call().get(s));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public RedisUserDao getDao()
    {
        return dao;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public boolean handleJoin(UUID uniqueId, String name)
    {
        UOfflineUser user = getDao().find(uniqueId);
        Ban ban = null;

        if (user == null)
        {
            user = new UUser(uniqueId);

            getDao().insert(user);
        }
        else
        {
            user = new UUser(uniqueId, user.getProfiles().values());

            ban = API.getBanManager().getBan(user);

            if (ban == null || !ban.isActive())
            {
//                ban = API.getBanManager().getBan(user.getStandardProfile().getEntityValue(StandardProfileKey.MOST_RECENT_IP.getKey()));
            }
        }


        getUsers().add((User) user);

        if (ban != null && ban.isActive())
        {
            ((User) user).kick(API.getBanManager().getBanMessage(ban));

            return false;
        }

        API.getModuleManger().triggerEvent(new UserConnectedEvent((User) user));
        return true;
    }

    public void handleQuit(UUID uniqueId)
    {
        UUser user = (UUser) findByUniqueId(uniqueId);

        getUsers().remove(user);

        API.getModuleManger().triggerEvent(new UserDisconnectedEvent(user));
    }
}
