package net.hcfpvp.core.natives.bungee.command;

import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.module.command.CommandCallerType;
import net.hcfpvp.api.framework.module.command.CommandListener;
import net.hcfpvp.api.framework.user.User;
import net.hcfpvp.core.framework.console.UConsoleUser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Wout on 17/04/2017.
 */
public class NativeBungeeCommand extends Command
{
    private final CommandListener listener;

    public NativeBungeeCommand(CommandListener listener)
    {
        super(listener.getCommand(), "hcf.command",
              listener.getAliases().toArray(new String[listener.getAliases().size()]));

        this.listener = listener;
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        CommandSender console = ProxyServer.getInstance().getConsole();

        if (!(sender instanceof ProxiedPlayer))
        {
            if (listener.isPlayerOnly())
            {
                sender.sendMessage(ChatColor.RED + "This command is player only.");
                return;
            }

            listener.execute(new UConsoleUser(API.getServer().getName()), CommandCallerType.CONSOLE,
                             args);
            return;
        }

        User user = API.getUserManager().findByUniqueId(((ProxiedPlayer) sender).getUniqueId());

        if (user.getRank().getGroup().getLadder() < listener.getMinGroup().getLadder() && user.getRank().getGroup().getLadder() < listener.getMinGroup().getLadder())
        {
            sender.sendMessage(ChatColor.RED + "You do not have enough permissions to execute that command!");
            return;
        }

        listener.execute(user, CommandCallerType.USER, args);
    }
}
