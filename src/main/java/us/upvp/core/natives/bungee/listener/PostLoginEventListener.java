package us.upvp.core.natives.bungee.listener;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import us.upvp.api.API;
import us.upvp.core.framework.user.UUserManager;

/**
 * Created by Wout on 15/04/2017.
 */
public class PostLoginEventListener implements Listener
{
    @EventHandler
    public void onPostLogin(PostLoginEvent e)
    {
        ((UUserManager) API.getUserManager()).handleJoin(e.getPlayer().getUniqueId(), e.getPlayer().getName());
    }
}
