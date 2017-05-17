package net.hcfpvp.core.natives.bukkit.listener;

import net.hcfpvp.api.API;
import net.hcfpvp.core.framework.user.UUserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Wout on 16/04/2017.
 */
public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        ((UUserManager) API.getUserManager()).handleJoin(e.getPlayer().getUniqueId(), e.getPlayer().getName());
    }
}
