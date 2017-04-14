package us.upvp.core.framework.server.hcfactions;

import us.upvp.api.framework.server.hcfactions.Faction;
import us.upvp.api.framework.server.hcfactions.FactionManager;
import us.upvp.api.framework.user.User;

import java.util.List;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class UFactionManager implements FactionManager
{
    public Faction findByUniqueId(UUID uuid)
    {
        return null;
    }

    public Faction findByUser(User user)
    {
        return null;
    }

    public List<Faction> findByString(String s)
    {
        return null;
    }
}
