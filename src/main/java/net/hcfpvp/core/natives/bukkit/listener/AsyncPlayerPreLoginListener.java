package net.hcfpvp.core.natives.bukkit.listener;

import net.hcfpvp.api.API;
import net.hcfpvp.core.framework.user.UUserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * Created by Wout on 16/04/2017.
 */
public class AsyncPlayerPreLoginListener implements Listener
{
    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e)
    {
        ((UUserManager) API.getUserManager()).handleJoin(e.getUniqueId(), e.getName());
    }
}
