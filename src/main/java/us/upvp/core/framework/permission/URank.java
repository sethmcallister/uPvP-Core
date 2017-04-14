package us.upvp.core.framework.permission;

import us.upvp.api.framework.permission.Group;
import us.upvp.api.framework.permission.Rank;
import us.upvp.api.framework.server.Server;

/**
 * Created by Wout on 14/04/2017.
 */
public class URank implements Rank
{
    private final Group primary;
    private final Group secondary;
    private final Server server;

    public URank(Group primary, Group secondary, Server server)
    {
        this.primary = primary;
        this.secondary = secondary;
        this.server = server;
    }

    public Group getPrimaryGroup()
    {
        return primary;
    }

    public Group getSecondaryGroup()
    {
        return secondary;
    }

    public Server getApplicableServer()
    {
        return server;
    }
}
