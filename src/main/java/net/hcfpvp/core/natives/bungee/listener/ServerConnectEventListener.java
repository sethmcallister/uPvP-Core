package net.hcfpvp.core.natives.bungee.listener;

import net.hcfpvp.api.framework.entity.EntityId;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.hcfpvp.core.framework.entity.UIPEntity;
import net.hcfpvp.core.framework.user.UUser;
import net.hcfpvp.core.framework.user.UUserManager;
import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.user.profile.StandardProfileKey;

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

        u.getStandardProfile()
         .getValues()
         .put(StandardProfileKey.LAST_KNOWN_USERNAME.getKey(), e.getPlayer().getName());
        u.getStandardProfile()
         .getValues()
         .put(StandardProfileKey.MOST_RECENT_IP.getKey(),
              new UIPEntity(e.getPlayer().getAddress().getAddress().getHostAddress()));
        ((Set<EntityId>) u.getStandardProfile()
                          .getValues()
                          .get(StandardProfileKey.IPS.getKey()))
                       .add(new UIPEntity(e.getPlayer().getAddress().getAddress().getHostAddress()));
        u.getStandardProfile()
         .getValues()
         .put(StandardProfileKey.LAST_SEEN.getKey(),
              System.currentTimeMillis());

        u.update();
    }
}
