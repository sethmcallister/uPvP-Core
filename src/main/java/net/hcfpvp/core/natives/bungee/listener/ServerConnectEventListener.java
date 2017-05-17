package net.hcfpvp.core.natives.bungee.listener;

import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.entity.EntityId;
import net.hcfpvp.api.framework.user.profile.StandardProfileKey;
import net.hcfpvp.api.profiles.core.StandardProfile;
import net.hcfpvp.core.framework.entity.UIPEntity;
import net.hcfpvp.core.framework.user.UUser;
import net.hcfpvp.core.framework.user.UUserManager;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Set;

/**
 * Created by Wout on 15/04/2017.
 */
public class ServerConnectEventListener implements Listener
{
    @EventHandler
    public void onServerConnect(ServerConnectEvent e)
    {
        boolean allow = ((UUserManager) API.getUserManager()).handleJoin(e.getPlayer().getUniqueId(),
                                                                         e.getPlayer().getName());

        if (!allow)
        {
            e.setCancelled(true);
            return;
        }

        UUser u = (UUser) API.getUserManager().findByUniqueId(e.getPlayer().getUniqueId());
        StandardProfile profile = (StandardProfile) u.getStandardProfile();

        profile.setLastKnownUsername(e.getPlayer().getName());
        profile.setMostRecentIP(new UIPEntity(e.getPlayer().getAddress().getAddress().getHostAddress()));
        profile.getIPs().add(new UIPEntity(e.getPlayer().getAddress().getAddress().getHostAddress()));
        profile.setLastSeenDate(System.currentTimeMillis());

        u.update();
    }
}
