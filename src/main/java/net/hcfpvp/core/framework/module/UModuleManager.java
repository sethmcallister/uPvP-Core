package net.hcfpvp.core.framework.module;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import net.hcfpvp.api.framework.config.Config;
import net.hcfpvp.api.framework.module.ModuleLoadException;
import net.hcfpvp.api.framework.module.ModuleManager;
import net.hcfpvp.api.framework.module.PluginModule;
import net.hcfpvp.api.framework.module.command.CommandCaller;
import net.hcfpvp.api.framework.module.command.CommandCallerType;
import net.hcfpvp.api.framework.module.event.Event;
import net.hcfpvp.api.framework.server.Server;
import net.hcfpvp.api.profiles.ProfileHandler;
import net.hcfpvp.core.framework.config.UConfig;
import net.hcfpvp.core.framework.server.UServer;
import net.hcfpvp.core.util.FileUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

/**
 * Created by Wout on 15/04/2017.
 */
public class UModuleManager implements ModuleManager
{
    private final List<PluginModule> modules;
    private final Server server;
    private final ProfileHandler profileHandler;
    private final Path dirPath;

    public UModuleManager(Server server, ProfileHandler profileHandler)
    {
        this.modules = Lists.newArrayList();
        this.server = server;
        this.profileHandler = profileHandler;
        this.dirPath = ((UServer) server).getPluginDir();

        loadModules(dirPath);
    }

    private void loadModules(Path dirPath)
    {
        Path modulesPath = Paths.get(dirPath.toAbsolutePath().toString(), "modules");

        FileUtil.createDirIfNotExists(modulesPath);

        try (Stream<Path> paths = Files.walk(modulesPath))
        {
            paths.filter(p -> p.toString().endsWith(".jar")).forEach(p ->
                                                                     {
                                                                         try
                                                                         {
                                                                             PluginModule module = loadModule(p);

                                                                             if (module != null)
                                                                             {
                                                                                 server.getPlugin()
                                                                                       .runAsync(() -> enableModule(
                                                                                               module));
                                                                             }
                                                                         }
                                                                         catch (ModuleLoadException e)
                                                                         {
                                                                             System.out.println(
                                                                                     "Error loading module %s!");
                                                                         }
                                                                     });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private PluginModule loadModule(String name) throws ModuleLoadException
    {
        return loadModule(Paths.get(dirPath.toAbsolutePath().toString(), "modules", name + ".jar"));
    }

    private PluginModule loadModule(Path path) throws ModuleLoadException
    {
        try
        {
            if (!Files.exists(path))
            {
                throw new ModuleLoadException(
                        String.format("Could not find module at path %s!", path.toAbsolutePath()));
            }

            JarFile file = new JarFile(path.toFile());
            ZipEntry moduleYaml = file.getEntry("module.yml");

            if (moduleYaml == null)
            {
                throw new ModuleLoadException(
                        String.format("Could not find module.yml at path %s!", path.toAbsolutePath()));
            }

            String moduleInfoString = CharStreams.toString(new InputStreamReader(
                    file.getInputStream(moduleYaml), Charsets.UTF_8));

            HashMap<String, Object> moduleInfo = (HashMap<String, Object>) new Yaml().load(moduleInfoString);

            String name = (String) moduleInfo.get("name");
            String version = (String) moduleInfo.get("version");
            String main = (String) moduleInfo.get("main");

            URLClassLoader child = new URLClassLoader(new URL[] { path.toUri().toURL() }, getClass().getClassLoader());
            Class mainClass = Class.forName(main, true, child);

            PluginModule module = (PluginModule) mainClass.newInstance();

            Field nameField = module.getClass().getSuperclass().getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(module, name);
            nameField.setAccessible(false);

            Field versionField = module.getClass().getSuperclass().getDeclaredField("version");
            versionField.setAccessible(true);
            versionField.set(module, version);
            versionField.setAccessible(false);

            ZipEntry configYaml = file.getEntry("config.yml");

            if (configYaml != null)
            {
                Path configDirPath = Paths.get(path.toAbsolutePath().toString(), name);

                Config config = new UConfig(configDirPath, "config.yml", file.getInputStream(configYaml));

                Field configField = module.getClass().getSuperclass().getDeclaredField("config");
                configField.setAccessible(true);
                configField.set(module, config);
                configField.setAccessible(false);
            }

            return module;
        }
        catch (Exception e)
        {
            throw new ModuleLoadException(
                    String.format("Failed to load module %s! Message: %s", path.getFileName().toString(),
                                  e.getMessage()));
        }
    }

    @Override
    public List<PluginModule> getAllEnabledModules()
    {
        return modules;
    }

    @Override
    public PluginModule findEnabledModule(String name)
    {
        return modules.stream().filter(m -> m.getModuleName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void enableModule(PluginModule module)
    {
        if (isEnabled(module))
        {
            throw new RuntimeException("Cannot enable an enabled module!");
        }

        module.onEnable();

        module.getCommandListeners()
              .forEach(l -> server.getPlugin().registerCommand(l));

        module.getProfiles().forEach((k, v) -> profileHandler.getProfiles().put(k, v));

        System.out.println(String.format("Loaded module %s!", module.getModuleName()));

        modules.add(module);
    }

    public void enableModule(String name) throws ModuleLoadException
    {
        PluginModule module = loadModule(name);
        enableModule(module);
    }

    public void disableModule(PluginModule module)
    {
        if (module == null || !isEnabled(module))
        {
            throw new RuntimeException("Cannot disable an disabled module!");
        }

        module.onDisable();

        modules.remove(module);
    }

    @Override
    public void disableModule(String name)
    {
        disableModule(findEnabledModule(name));
    }

    public boolean isEnabled(PluginModule module)
    {
        return modules.stream().anyMatch(m -> m.getModuleName().equalsIgnoreCase(module.getModuleName()));
    }

    @Override
    public boolean isEnabled(String name)
    {
        return findEnabledModule(name) != null;
    }

    @Override
    public void triggerEvent(Event event)
    {
        getAllEnabledModules().forEach(m -> m.getEventListeners()
                                             .stream()
                                             .filter(e -> e.getType().equals(event.getClass()))
                                             .forEach(e -> e.handle(event)));
    }

    @Override
    public void triggerCommand(CommandCaller caller, CommandCallerType type, String cmd)
    {
        String command = cmd.split(" ")[0];

        getAllEnabledModules().stream()
                              .map(PluginModule::getCommandListeners)
                              .collect(Collectors.toList())
                              .stream()
                              .flatMap(List::stream)
                              .collect(Collectors.toList())
                              .stream()
                              .filter(cl -> cl.getCommand().equalsIgnoreCase(command) ||
                                            cl.getAliases().stream().anyMatch(a -> a.equalsIgnoreCase(command)))
                              .forEach(cl -> cl.execute(caller, type, cmd.split(" ")));
    }
}
