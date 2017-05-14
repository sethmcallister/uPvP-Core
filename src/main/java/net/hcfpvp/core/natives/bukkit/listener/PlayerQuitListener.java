package net.hcfpvp.core.natives.bukkit.listener;

import net.hcfpvp.core.framework.user.UUserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import net.hcfpvp.api.API;

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
