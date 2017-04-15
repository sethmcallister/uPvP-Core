package us.upvp.core.framework.core;

import us.upvp.api.framework.ban.BanManager;
import us.upvp.api.framework.core.Core;
import us.upvp.api.framework.module.ModuleManager;
import us.upvp.api.framework.mute.MuteManager;
import us.upvp.api.framework.registration.RegistrationManager;
import us.upvp.api.framework.server.Server;
import us.upvp.api.framework.time.TimeFormatter;
import us.upvp.api.framework.user.UserManager;
import us.upvp.api.framework.util.UUIDFetcher;
import us.upvp.core.framework.ban.UBanManager;
import us.upvp.core.framework.module.UModuleManager;
import us.upvp.core.framework.mute.UMuteManager;
import us.upvp.core.framework.registration.URegistrationManager;
import us.upvp.core.framework.time.UTimeFormatter;
import us.upvp.core.framework.user.UUserManager;
import us.upvp.core.framework.util.UUUIDFetcher;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Wout on 14/04/2017.
 */
public class UCore implements Core
{
    private final UserManager userManager;
    private final BanManager banManager;
    private final RegistrationManager registrationManager;
    private final MuteManager muteManager;
    private final ModuleManager moduleManager;
    private final TimeFormatter timeFormatter;
    private final UUIDFetcher uuidFetcher;
    private final Logger logger;
    private final Server server;
    private final Object plugin;

    public UCore(Server server, Object plugin)
    {
        this.userManager = new UUserManager();
        this.banManager = new UBanManager();
        this.registrationManager = new URegistrationManager();
        this.muteManager = new UMuteManager();
        this.moduleManager = new UModuleManager(server);
        this.timeFormatter = new UTimeFormatter();
        this.uuidFetcher = new UUUIDFetcher(new ArrayList<String>());
        this.logger = getServer().getLogger();
        this.server = server;
        this.plugin = plugin;
    }

    public UserManager getUserManager()
    {
        return userManager;
    }

    public BanManager getBanManager()
    {
        return banManager;
    }

    public RegistrationManager getRegistrationManager()
    {
        return registrationManager;
    }

    public MuteManager getMuteManager()
    {
        return muteManager;
    }

    public ModuleManager getModuleManager()
    {
        return moduleManager;
    }

    public TimeFormatter getTimeFormatter()
    {
        return timeFormatter;
    }

    public UUIDFetcher getUUIDFetcher()
    {
        return uuidFetcher;
    }

    public Logger getLogger()
    {
        return logger;
    }

    public Server getServer()
    {
        return server;
    }

    public Object getPlugin()
    {
        return plugin;
    }
}
