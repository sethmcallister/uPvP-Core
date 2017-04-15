package us.upvp.core.natives.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
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
public class NativeBukkitPlugin extends JavaPlugin
{
    private Server server;
    private YamlConfiguration config;

    @Override
    public void onLoad()
    {
        boolean ok = loadConfig();

        if (ok)
        {
            server = new UServer(config.getString("server-name"), ServerType.BUKKIT, getLogger(), this,
                                 getDataFolder().toPath());
        }
        else
        {
            Bukkit.getServer().shutdown();
        }

    }

    @Override
    public void onDisable()
    {

    }

    @Override
    public void onEnable()
    {

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
                Files.copy(getResource("config.yml"), filePath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }

        config = YamlConfiguration.loadConfiguration(filePath.toFile());

        return true;
    }
}
