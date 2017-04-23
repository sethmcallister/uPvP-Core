package us.upvp.core.framework.user;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import us.upvp.api.API;
import us.upvp.api.framework.module.event.events.UserConnectedEvent;
import us.upvp.api.framework.module.event.events.UserDisconnectedEvent;
import us.upvp.api.framework.permission.Group;
import us.upvp.api.framework.user.OfflineUser;
import us.upvp.api.framework.user.User;
import us.upvp.api.framework.user.UserManager;
import us.upvp.core.framework.data.RedisDatabaseManager;
import us.upvp.core.framework.data.model.RedisUserDao;
import us.upvp.core.framework.permission.URank;
import us.upvp.core.framework.user.profile.UHCFProfile;
import us.upvp.core.framework.user.profile.UPracticeProfile;
import us.upvp.core.framework.util.UUIDFetcher;

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
    private final RedisDatabaseManager databaseManager;

    public UUserManager(RedisDatabaseManager manager)
    {
        this.dao = manager.getUserDao();
        this.users = Lists.newArrayList();
        this.databaseManager = manager;
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

    public void handleJoin(UUID uniqueId, String name)
    {
        UOfflineUser user = getDao().find(uniqueId);

        if (user == null)
        {
            user = new UUser(uniqueId, name, Lists.newArrayList(new URank(Group.MEMBER, "___GLOBAL___")),
                             new UPracticeProfile(), new UHCFProfile(),
                             Lists.newArrayList(), Lists.newArrayList(),
                             API.getTimeFormatter().getFormatted(System.currentTimeMillis()), "");

            getDao().insert(user);
        }
        else
        {
            user = convert(user);
        }

        getUsers().add((User) user);

        API.getModuleManger().triggerEvent(new UserConnectedEvent((User) user));
    }

    public UUser convert(UOfflineUser user)
    {
        return new UUser(user.getUniqueId(), user.getLastName(), user.getRanks(), user.getPractice(),
                         user.getHCFactions(), user.getAllIPs(), user.getIgnoredList(), user.getJoinedDate(),
                         user.getPassword());
    }

    public void handleQuit(UUID uniqueId)
    {
        UUserManager manager = (UUserManager) API.getUserManager();
        UUser user = (UUser) manager.findByUniqueId(uniqueId);

        manager.getUsers().remove(user);

        API.getModuleManger().triggerEvent(new UserDisconnectedEvent(user));
    }

    public RedisDatabaseManager getDatabaseManager()
    {
        return databaseManager;
    }
}
