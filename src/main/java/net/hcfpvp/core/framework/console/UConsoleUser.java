package net.hcfpvp.core.framework.console;

import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.console.ConsoleUser;

/**
 * Created by Wout on 18/04/2017.
 */
public class UConsoleUser implements ConsoleUser
{
    private final String name;

    public UConsoleUser(String name)
    {
        this.name = name;
    }

    @Override
    public String getEntityIdentifier()
    {
        return String.format("CONSOLE|%s", name);
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
