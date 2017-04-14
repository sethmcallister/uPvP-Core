package us.upvp.core.framework.user;

import com.google.common.collect.ImmutableList;
import us.upvp.api.framework.user.User;
import us.upvp.api.framework.user.UserManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class UUserManager implements UserManager
{
    public User findByUniqueId(UUID uuid)
    {
        return null;
    }

    public ImmutableList<User> findAllUsers()
    {
        return null;
    }

    public User findOfflineByUniqueId(UUID uuid)
    {
        return null;
    }

    public List<User> findAllUsersAnyway()
    {
        return null;
    }

    public User findOfflineByName(String s)
    {
        return null;
    }
}
