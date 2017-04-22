package us.upvp.core.natives.bukkit.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import us.upvp.api.API;
import us.upvp.api.framework.module.command.CommandCallerType;
import us.upvp.api.framework.module.command.CommandListener;
import us.upvp.api.framework.user.User;
import us.upvp.core.framework.console.UConsoleUser;

/**
 * Created by Wout on 17/04/2017.
 */
public class NativeBukkitCommand extends BukkitCommand
{
    private final CommandListener listener;

    public NativeBukkitCommand(CommandListener listener)
    {
        super(listener.getCommand(), listener.getDescription(), listener.getUsage(), listener.getAliases());

        this.listener = listener;
    }

    @Override
    public boolean execute(CommandSender sender, String cmd, String[] args)
    {
        if (sender instanceof ConsoleCommandSender)
        {
            if (listener.isPlayerOnly())
            {
                sender.sendMessage(ChatColor.RED + "This command is player only.");
                return false;
            }

            listener.execute(new UConsoleUser(), CommandCallerType.CONSOLE,
                             args);
            return true;
        }

        User user = API.getUserManager().findByUniqueId(((Player) sender).getUniqueId());

        if (user.getRank().getGroup().getLadder() < listener.getMinGroup().getLadder() &&
            user.getRank().getGroup().getLadder() < listener.getMinGroup().getLadder())
        {
            sender.sendMessage(ChatColor.RED + "You do not have enough permissions to execute that command!");
            return false;
        }

        listener.execute(user, CommandCallerType.USER, args);
        return true;
    }
}
