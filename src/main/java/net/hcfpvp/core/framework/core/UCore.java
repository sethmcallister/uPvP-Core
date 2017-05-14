package net.hcfpvp.core.framework.core;

import net.hcfpvp.core.framework.ban.UBanManager;
import net.hcfpvp.core.framework.module.UModuleManager;
import net.hcfpvp.core.framework.mute.UMuteManager;
import net.hcfpvp.core.framework.profile.ProfileHandler;
import net.hcfpvp.core.framework.time.UTimeFormatter;
import net.hcfpvp.core.framework.user.UUserManager;
import net.hcfpvp.core.framework.util.UUUIDFetcher;
import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.ban.BanManager;
import net.hcfpvp.api.framework.core.Core;
import net.hcfpvp.api.framework.data.RedisDataManager;
import net.hcfpvp.api.framework.module.ModuleManager;
import net.hcfpvp.api.framework.mute.MuteManager;
import net.hcfpvp.api.framework.server.NativeFunctionality;
import net.hcfpvp.api.framework.server.Server;
import net.hcfpvp.api.framework.time.TimeFormatter;
import net.hcfpvp.api.framework.user.UserManager;
import net.hcfpvp.api.framework.util.UUIDFetcher;
import net.hcfpvp.core.framework.data.RedisDatabaseManager;

import java.util.logging.Logger;

/**
 * Created by Wout on 14/04/2017.
 */
public class UCore implements Core
{
    private final RedisDatabaseManager databaseManager;
    private final UserManager userManager;
    private final BanManager banManager;
    private final MuteManager muteManager;
    private final ProfileHandler profileHandler;
    private final NativeFunctionality plugin;
    private final ModuleManager moduleManager;
    private final TimeFormatter timeFormatter;
    private final UUIDFetcher uuidFetcher;
    private final Logger logger;
    private final Server server;

    public UCore(Server server, NativeFunctionality plugin)
    {
        API.setCore(this);

        this.profileHandler = new ProfileHandler();

        this.databaseManager = new RedisDatabaseManager(server, profileHandler);

        this.userManager = new UUserManager(databaseManager);
        this.banManager = new UBanManager(databaseManager);
        this.muteManager = new UMuteManager(databaseManager);
        this.plugin = plugin;
        this.timeFormatter = new UTimeFormatter();
        this.uuidFetcher = new UUUIDFetcher();
        this.server = server;
        this.logger = getServer().getLogger();
        this.moduleManager = new UModuleManager(server, profileHandler);
    }

    public UserManager getUserManager()
    {
        return userManager;
    }

    public BanManager getBanManager()
    {
        return banManager;
    }

    public MuteManager getMuteManager()
    {
        return muteManager;
    }

    public ModuleManager getModuleManager()
    {
        return moduleManager;
    }

    @Override
    public RedisDataManager getDataManager()
    {
        return databaseManager;
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

    public NativeFunctionality getPlugin()
    {
        return plugin;
    }

    public ProfileHandler getProfileHandler()
    {
        return profileHandler;
    }
}
