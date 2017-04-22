package us.upvp.core.natives.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import us.upvp.api.API;
import us.upvp.core.framework.user.UUserManager;

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
