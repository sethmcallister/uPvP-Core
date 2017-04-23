package us.upvp.core.natives.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import us.upvp.api.API;
import us.upvp.api.framework.module.command.CommandListener;
import us.upvp.api.framework.server.NativeFunctionality;
import us.upvp.api.framework.server.Server;
import us.upvp.api.framework.server.ServerType;
import us.upvp.api.framework.user.User;
import us.upvp.core.framework.config.UConfig;
import us.upvp.core.framework.server.UServer;
import us.upvp.core.natives.bukkit.command.NativeBukkitCommand;
import us.upvp.core.natives.bukkit.listener.AsyncPlayerPreLoginListener;
import us.upvp.core.natives.bukkit.listener.PlayerQuitListener;

import java.lang.reflect.Method;

/**
 * Created by Wout on 14/04/2017.
 */
public class NativeBukkitPlugin extends JavaPlugin implements NativeFunctionality
{
    @Override
    public void onDisable()
    {
        API.getRedisDataManager().getClient().shutdown();
    }

    @Override
    public void onEnable()
    {
        Server server = new UServer(ServerType.BUKKIT,
                                    new UConfig(getDataFolder().toPath(), "config.yml", getResource("config.yml")),
                                    getLogger(), getDataFolder().toPath(), this);

        getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    @Override
    public void sendMessage(User user, String message)
    {
        Bukkit.getPlayer(user.getUniqueId()).sendMessage(message);
    }

    @Override
    public void runAsync(Runnable r)
    {
        getServer().getScheduler().runTaskAsynchronously(this, r);
    }

    @Override
    public void registerCommand(CommandListener listener)
    {
        try
        {
            Method commandMap = getServer().getClass().getMethod("getCommandMap", null);
            Object cmdmap = commandMap.invoke(getServer(), null);
            Method register = cmdmap.getClass().getMethod("register", String.class, Command.class);
            register.invoke(cmdmap, listener.getCommand(), new NativeBukkitCommand(listener));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
