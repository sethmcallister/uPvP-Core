package us.upvp.core.natives.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import us.upvp.api.framework.server.Server;
import us.upvp.api.framework.server.ServerType;
import us.upvp.core.framework.server.UServer;
import us.upvp.core.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Wout on 14/04/2017.
 */
public class NativeBungeePlugin extends Plugin
{
    private Server server;
    private Configuration config;

    @Override
    public void onLoad()
    {
        boolean ok = loadConfig();

        if (ok)
            server = new UServer(config.getString("server-name"), ServerType.PROXY, getLogger(), this, getDataFolder().toPath());
        else
            getProxy().stop();
    }

    private boolean loadConfig()
    {
        Path dirPath = getDataFolder().toPath();

        FileUtil.createDirIfNotExists(dirPath);

        Path filePath = Paths.get(dirPath.toAbsolutePath().toString(), "config.yml");

        if (!Files.exists(filePath))
        {
            try
            {
                Files.copy(getResourceAsStream("config.yml"), filePath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }

        try
        {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(filePath.toFile());
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onEnable()
    {

    }

    @Override
    public void onDisable()
    {

    }
}
