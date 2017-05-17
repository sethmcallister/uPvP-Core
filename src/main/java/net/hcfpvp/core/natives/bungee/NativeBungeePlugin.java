package net.hcfpvp.core.natives.bungee;

import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.module.command.CommandListener;
import net.hcfpvp.api.framework.server.NativeFunctionality;
import net.hcfpvp.api.framework.server.Server;
import net.hcfpvp.api.framework.server.ServerType;
import net.hcfpvp.api.framework.user.User;
import net.hcfpvp.core.framework.config.UConfig;
import net.hcfpvp.core.framework.server.UServer;
import net.hcfpvp.core.natives.bungee.command.NativeBungeeCommand;
import net.hcfpvp.core.natives.bungee.listener.PlayerDisconnectEventListener;
import net.hcfpvp.core.natives.bungee.listener.PlayerSwitchServerEventListener;
import net.hcfpvp.core.natives.bungee.listener.ServerConnectEventListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

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

        getProxy().getPluginManager().registerListener(this, new ServerConnectEventListener());
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnectEventListener());
        getProxy().getPluginManager().registerListener(this, new PlayerSwitchServerEventListener());
    }

    @Override
    public void onDisable()
    {
        API.getRedisDataManager().getJedisPool().destroy();
    }

    @Override
    public void sendMessage(User user, String message)
    {
        getProxy().getPlayer(user.getUniqueId()).sendMessage(ChatColor.translateAlternateColorCodes('&', message));
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

    @Override
    public void kick(User user, String s)
    {
        getProxy().getPlayer(user.getUniqueId()).disconnect(ChatColor.translateAlternateColorCodes('&', s));
    }
}
