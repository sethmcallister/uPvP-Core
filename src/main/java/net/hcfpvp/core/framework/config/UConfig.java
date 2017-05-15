package net.hcfpvp.core.framework.config;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import net.hcfpvp.api.framework.config.Config;
import net.hcfpvp.core.util.FileUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by Wout on 15/04/2017.
 */
public class UConfig implements Config
{
    private String yaml;
    private HashMap<String, Object> values;

    public UConfig(String yaml)
    {
        this.yaml = yaml;
        this.values = new HashMap<>();
    }

    public UConfig(Path file)
    {
        loadFromFile(file);
    }

    public UConfig(Path dir, String name, InputStream file)
    {
        FileUtil.createDirIfNotExists(dir);

        Path filePath = Paths.get(dir.toAbsolutePath().toString(), name);

        if (!Files.exists(filePath))
        {
            try
            {
                Files.copy(file, filePath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        loadFromFile(filePath);

        load();
    }

    private void loadFromFile(Path file)
    {
        try
        {
            yaml = CharStreams.toString(new InputStreamReader(
                    Files.newInputStream(file), Charsets.UTF_8));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void load()
    {
        values = (HashMap<String, Object>) new Yaml().load(yaml);
    }

    @Override
    public String getString(String s)
    {
        return (String) values.getOrDefault(s, null);
    }

    @Override
    public Character getChar(String s)
    {
        return ((String) values.getOrDefault(s, null)).toCharArray()[0];
    }

    @Override
    public Short getShort(String s)
    {
        return new Short(String.valueOf(values.getOrDefault(s, null)));
    }

    @Override
    public Integer getInt(String s)
    {
        return (Integer) values.getOrDefault(s, null);
    }

    @Override
    public Long getLong(String s)
    {
        return (Long) values.getOrDefault(s, null);
    }

    @Override
    public Float getFloat(String s)
    {
        return Float.valueOf(String.valueOf(values.getOrDefault(s, null)));
    }

    @Override
    public Double getDouble(String s)
    {
        return (Double) values.getOrDefault(s, null);
    }

    @Override
    public Boolean getBoolean(String s)
    {
        return (Boolean) values.getOrDefault(s, null);
    }

    @Override
    public Object get(String s)
    {
        return values.getOrDefault(s, null);
    }

    @Override
    public boolean hasKey(String s)
    {
        return values.containsKey(s);
    }
}
