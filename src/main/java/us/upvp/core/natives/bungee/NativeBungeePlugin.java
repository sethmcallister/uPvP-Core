package us.upvp.core.natives.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import us.upvp.api.API;
import us.upvp.api.framework.module.command.CommandListener;
import us.upvp.api.framework.server.NativeFunctionality;
import us.upvp.api.framework.server.Server;
import us.upvp.api.framework.server.ServerType;
import us.upvp.api.framework.user.User;
import us.upvp.core.framework.config.UConfig;
import us.upvp.core.framework.server.UServer;
import us.upvp.core.natives.bungee.command.NativeBungeeCommand;
import us.upvp.core.natives.bungee.listener.PlayerDisconnectEventListener;
import us.upvp.core.natives.bungee.listener.PostLoginEventListener;

/**
 * Created by Wout on 14/04/2017.
 */
public class NativeBungeePlugin extends Plugin implements NativeFunctionality
{
    @Override
    public void onEnable()
    {
        Server server = new UServer(ServerType.PROXY, new UConfig(getDataFolder().toPath(), "config.yml",
                                                                  getResourceAsStream("config.yml")), getLogger(),
                                    getDataFolder().toPath(), this);

        getProxy().getPluginManager().registerListener(this, new PostLoginEventListener());
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnectEventListener());
    }

    @Override
    public void onDisable()
    {
        API.getRedisDataManager().getClient().shutdown();
    }

    @Override
    public void sendMessage(User user, String message)
    {
        getProxy().getPlayer(user.getUniqueId()).sendMessage(message);
    }

    @Override
    public void runAsync(Runnable r)
    {
        getProxy().getScheduler().runAsync(this, r);
    }

    @Override
    public void registerCommand(CommandListener listener)
    {
        getProxy().getPluginManager().registerCommand(this, new NativeBungeeCommand(listener));
    }
}
