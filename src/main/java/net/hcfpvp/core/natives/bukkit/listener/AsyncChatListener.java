package net.hcfpvp.core.natives.bukkit.listener;

import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.core.Core;
import net.hcfpvp.api.framework.mute.Mute;
import net.hcfpvp.api.framework.mute.MuteType;
import net.hcfpvp.api.framework.permission.Rank;
import net.hcfpvp.api.framework.user.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Sethy on 02/06/2017.
 */
public class AsyncChatListener implements Listener
{
    private final Map<UUID, Long> cooldowns = new ConcurrentHashMap<>();

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent event)
    {
        final User user = API.getUserManager().findByUniqueId(event.getPlayer().getUniqueId());
        Mute mute = API.getMuteManager().getMute(user);
        if (mute != null)
        {
            if (mute.getType().equals(MuteType.NORMAL_PERMANENT))
            {
                final Player player = event.getPlayer();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have been muted, this mute never expires!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou where muted for the reason: &a" + mute.getReason() + "&e."));
                event.setCancelled(true);
                return;
            }
            if (mute.getType().equals(MuteType.NORMAL_TEMPORARILY))
            {
                Date expire = mute.getExpireDate();
                Date now = new Date();
                if (expire.after(now))
                {
                    final Player player = event.getPlayer();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have been muted. This mute will expire on &a" + expire.toGMTString()));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou where muted for the reason: &3" + mute.getReason() + "&7."));
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if (this.cooldowns.containsKey(user.getUniqueId()) && this.cooldowns.get(user.getUniqueId()) > System.currentTimeMillis())
        {
            long millisLeft = this.cooldowns.get(user.getUniqueId()) - System.currentTimeMillis();
            double value = millisLeft / 1000.0D;
            double sec = Math.round(10.0D * value) / 10.0D;
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou cannot chat for another &a" + sec + "&e seconds."));
            event.setCancelled(true);
            return;
        }

        for(Rank rank : user.getRanks())
        {
            System.out.println(rank.getGroup() + " -> " + rank.getApplicableServer());
        }
        event.setFormat(ChatColor.translateAlternateColorCodes('&', user.getRank().getGroup().getColors() + event.getPlayer().getName() + " &6\u00bb&7 ") + "%2$s");

        Long time = 3000 + System.currentTimeMillis();
        cooldowns.put(event.getPlayer().getUniqueId(), time);
    }
}
