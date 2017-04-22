package us.upvp.core.natives.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import us.upvp.api.API;
import us.upvp.core.framework.user.UUserManager;

/**
 * Created by Wout on 16/04/2017.
 */
public class PlayerQuitListener implements Listener
{
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        ((UUserManager) API.getUserManager()).handleQuit(e.getPlayer().getUniqueId());
    }
}
