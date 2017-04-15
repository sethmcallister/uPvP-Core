package us.upvp.core.framework.module;

import org.yaml.snakeyaml.Yaml;
import us.upvp.api.framework.module.PluginModuleConfig;

import java.util.HashMap;

/**
 * Created by Wout on 15/04/2017.
 */
public class UPluginModuleConfig implements PluginModuleConfig
{
    private final String yaml;
    private HashMap<String, Object> values;

    public UPluginModuleConfig(String yaml)
    {
        this.yaml = yaml;
        this.values = new HashMap<>();
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
