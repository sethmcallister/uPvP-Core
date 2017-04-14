package us.upvp.core.framework.server;

import us.upvp.api.API;
import us.upvp.api.framework.core.Core;
import us.upvp.api.framework.server.Server;
import us.upvp.api.framework.server.ServerType;
import us.upvp.core.framework.core.UCore;

import java.nio.file.Path;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by Wout on 14/04/2017.
 */
public class UServer implements Server
{
    private final String name;
    private final ServerType type;
    private final Logger logger;
    private final Core core;
    private final Path pluginDirPath;

    public UServer(String name, ServerType type, Logger logger, Object plugin, Path pluginDirPath)
    {
        this.name = name;
        this.type = type;
        this.logger = logger;
        this.pluginDirPath = pluginDirPath;
        this.core = new UCore(this, plugin);

        API.setCore(core);
    }

    public ServerType getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public Logger getLogger()
    {
        return logger;
    }

    public Date getNextScheduledRestart()
    {
        return null;
    }

    public void setNextScheduledRestart(Date date)
    {
        // TODO scheduled restart
    }

    public Path getPluginDir()
    {
        return pluginDirPath;
    }
}
