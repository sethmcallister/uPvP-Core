package us.upvp.core.framework.module;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import org.yaml.snakeyaml.Yaml;
import us.upvp.api.framework.module.ModuleLoadException;
import us.upvp.api.framework.module.ModuleManager;
import us.upvp.api.framework.module.PluginModule;
import us.upvp.core.util.FileUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

/**
 * Created by Wout on 15/04/2017.
 */
public class UModuleManager implements ModuleManager
{
    private final List<PluginModule> modules;
    private final Path dirPath;

    public UModuleManager(Path dirPath)
    {
        this.modules = Lists.newArrayList();
        this.dirPath = dirPath;

        loadModules(dirPath);
    }

    private void loadModules(Path dirPath)
    {
        Path modulesPath = Paths.get(dirPath.toAbsolutePath().toString(), "modules");

        FileUtil.createDirIfNotExists(modulesPath);

        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.jar");

        try (Stream<Path> paths = Files.walk(modulesPath))
        {
            paths.filter(matcher::matches).forEach(p ->
                                                   {
                                                       try
                                                       {
                                                           loadModule(p);
                                                       }
                                                       catch (ModuleLoadException e)
                                                       {
                                                           System.err.print(e.getMessage());
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
                return null;
            }

            JarFile file = new JarFile(path.toFile());
            ZipEntry moduleYaml = file.getEntry("module.yml");

            if (moduleYaml == null)
            {
                return null;
            }

            String moduleInfoString = CharStreams.toString(new InputStreamReader(
                    file.getInputStream(moduleYaml), Charsets.UTF_8));

            HashMap<String, Object> moduleInfo = (HashMap<String, Object>) new Yaml().load(moduleInfoString);

            String name = (String) moduleInfo.get("name");
            String version = (String) moduleInfo.get("version");
            String main = (String) moduleInfo.get("main");

            URLClassLoader child = new URLClassLoader(new URL[] { path.toUri().toURL() }, getClass().getClassLoader());
            Class mainClass = Class.forName(main, true, child);

            return (PluginModule) mainClass.getConstructor(String.class, String.class).newInstance(name, version);
        }
        catch (Exception e)
        {
            throw new ModuleLoadException(String.format("Failed to load module %s!", path.getFileName().toString()));
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
        return modules.contains(module);
    }

    @Override
    public boolean isEnabled(String name)
    {
        return findEnabledModule(name) != null;
    }
}
