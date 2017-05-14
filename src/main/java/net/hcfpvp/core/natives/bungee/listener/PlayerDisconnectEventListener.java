package net.hcfpvp.core.natives.bungee.listener;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.hcfpvp.api.API;
import net.hcfpvp.core.framework.user.UUserManager;

/**
 * Created by Wout on 15/04/2017.
 */
public class PlayerDisconnectEventListener implements Listener
{
    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent e)
    {
        ((UUserManager) API.getUserManager()).handleQuit(e.getPlayer().getUniqueId());
    }
}
