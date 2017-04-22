package us.upvp.core.framework.console;

import us.upvp.api.API;
import us.upvp.api.framework.console.ConsoleUser;

/**
 * Created by Wout on 18/04/2017.
 */
public class UConsoleUser implements ConsoleUser
{
    @Override
    public String getEntityIdentifier()
    {
        return String.format("Type: %s, SERVER: %s", "Console", API.getServer().getName());
    }

    @Override
    public void sendMessage(String s)
    {
        API.getLogger().info(s);
    }

    @Override
    public void sendUnformattedMessage(String s)
    {
        API.getLogger().info(s);
    }
}
