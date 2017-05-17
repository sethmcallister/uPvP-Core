package net.hcfpvp.core.natives.bungee.listener;

import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.user.User;
import net.hcfpvp.api.profiles.core.StandardProfile;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Wout on 17/05/2017.
 */
public class PlayerSwitchServerEventListener implements Listener
{
    @EventHandler
    public void onPlayerSwitchServerEvent(ServerSwitchEvent e)
    {
        User user = API.getUserManager().findByUniqueId(e.getPlayer().getUniqueId());

        StandardProfile profile = (StandardProfile) user.getStandardProfile();
        profile.setCurrentServer(e.getPlayer().getServer().getInfo().getName().toLowerCase());

        user.update();
    }
}
