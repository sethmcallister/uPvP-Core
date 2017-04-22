package us.upvp.core.framework.module;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import us.upvp.api.framework.module.ModuleLoadException;
import us.upvp.api.framework.module.ModuleManager;
import us.upvp.api.framework.module.PluginModule;
import us.upvp.api.framework.server.NativeFunctionality;
import us.upvp.api.framework.server.ServerType;
import us.upvp.core.framework.server.UServer;
import us.upvp.core.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Wout on 15/04/2017.
 */
public class UModuleManagerTest
{
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private ModuleManager manager;
    private UServer mockServer;

    @Before
    public void setUp() throws Exception
    {
        mockServer = mock(UServer.class);

        NativeFunctionality nativeFunctionality = mock(NativeFunctionality.class);

        when(mockServer.getPluginDir()).thenReturn(tmpFolder.getRoot().toPath());
        when(mockServer.getPlugin()).thenReturn(nativeFunctionality);
        when(mockServer.getType()).thenReturn(ServerType.BUKKIT);
        doNothing().when(nativeFunctionality).runAsync(any());

        FileUtil.createDirIfNotExists(Paths.get(tmpFolder.getRoot().getAbsolutePath(), "modules"));

        manager = new UModuleManager(mockServer);

        Files.copy(getClass().getResourceAsStream("/uPvP-Core-Test-Module-1.0-SNAPSHOT.jar"),
                   new File(tmpFolder.getRoot(), "modules/valid-test-file.jar").toPath());
    }

    @Test
    public void loadingModulesThroughConstructor() throws IOException
    {
        Files.copy(getClass().getResourceAsStream("/uPvP-Core-Test-Ignore-Module-1.0-SNAPSHOT.jar"),
                   new File(tmpFolder.getRoot(), "modules/test-file.jar").toPath());

        new UModuleManager(mockServer);
    }

    @Test
    public void moduleWithInvalidModuleYamlWillThrowException() throws Exception
    {
        Files.copy(getClass().getResourceAsStream("/uPvP-Core-Test-Fail-Module-1.0-SNAPSHOT.jar"),
                   new File(tmpFolder.getRoot(), "modules/test-file.jar").toPath());

        thrown.expect(ModuleLoadException.class);
        thrown.expectMessage(
                String.format("Failed to load module %s! Message: %s", "test-file.jar", "while scanning a simple key"));

        manager.enableModule("test-file");
    }

    @Test
    public void moduleWithNoModuleYamlWillThrowException() throws Exception
    {
        Files.copy(getClass().getResourceAsStream("/uPvP-Core-Test-Ignore-Module-1.0-SNAPSHOT.jar"),
                   new File(tmpFolder.getRoot(), "modules/test-file.jar").toPath());

        thrown.expect(ModuleLoadException.class);
        thrown.expectMessage(String.format("Could not find module.yml at path %s!",
                                           new File(tmpFolder.getRoot(), "modules/test-file.jar").toPath()
                                                                                                 .toAbsolutePath()));

        manager.enableModule("test-file");
    }

    @Test
    public void validModuleWillBeLoaded() throws Exception
    {
        manager.enableModule("valid-test-file");

        assertEquals(1, manager.getAllEnabledModules().size());
    }

    @Test
    public void disableModuleRemovesModuleFromEnabledModules() throws Exception
    {
        manager.enableModule("valid-test-file");
        manager.disableModule(manager.getAllEnabledModules().get(0));

        assertEquals(0, manager.getAllEnabledModules().size());
    }

    @Test
    public void disableModuleStringRemovesModuleFromEnabledModules() throws Exception
    {
        manager.enableModule("valid-test-file");
        manager.disableModule(manager.getAllEnabledModules().get(0).getModuleName());

        assertEquals(0, manager.getAllEnabledModules().size());
    }

    @Test
    public void enableAValidModuleTwiceThrowsException() throws Exception
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Cannot enable an enabled module!");

        manager.enableModule("valid-test-file");
        manager.enableModule("valid-test-file");
    }

    @Test
    public void findEnabledModuleReturnsCorrectModule() throws Exception
    {
        manager.enableModule("valid-test-file");

        PluginModule module = manager.getAllEnabledModules().get(0);

        assertEquals(module.getModuleName(), manager.findEnabledModule(module.getModuleName()).getModuleName());
    }

    @Test
    public void isEnabledReturnsCorrectValueIfEnabled() throws Exception
    {
        manager.enableModule("valid-test-file");

        PluginModule module = manager.getAllEnabledModules().get(0);

        assertEquals(true, manager.isEnabled(module));
    }

    @Test
    public void isEnabledStringReturnsCorrectValueIfEnabled() throws Exception
    {
        manager.enableModule("valid-test-file");

        PluginModule module = manager.getAllEnabledModules().get(0);

        assertEquals(true, manager.isEnabled(module.getModuleName()));
    }

    @Test
    public void disableDisabledModuleThrowsError() throws Exception
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Cannot disable an disabled module!");

        manager.disableModule("test");
    }

    @Test
    public void loadModuleAtInvalidPathThrowsException() throws Exception
    {
        thrown.expect(ModuleLoadException.class);
        thrown.expectMessage(
                String.format("Could not find module at path %s!", new File(tmpFolder.getRoot(), "modules/test.jar")));

        manager.enableModule("test");
    }

    @After
    public void tearDown() throws Exception
    {
        tmpFolder.delete();
    }
}