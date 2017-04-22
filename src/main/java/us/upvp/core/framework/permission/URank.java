package us.upvp.core.framework.permission;

import us.upvp.api.framework.permission.Group;
import us.upvp.api.framework.permission.Rank;

/**
 * Created by Wout on 14/04/2017.
 */
public class URank implements Rank
{
    private Group group;
    private String server;

    public URank(Group group, String server)
    {
        this.group = group;
        this.server = server;
    }

    public Group getGroup()
    {
        return group;
    }

    public String getApplicableServer()
    {
        return server;
    }

    public boolean isGlobal()
    {
        return server.equals("___GLOBAL___");
    }

    public void setGroup(Group group)
    {
        this.group = group;
    }

    public void setServer(String server)
    {
        this.server = server;
    }
}
