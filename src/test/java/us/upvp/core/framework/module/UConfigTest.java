package us.upvp.core.framework.module;

import com.google.common.io.Resources;
import net.hcfpvp.api.framework.config.Config;
import net.hcfpvp.core.framework.config.UConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

/**
 * Created by Wout on 15/04/2017.
 */
public class UConfigTest
{
    private Config config;

    @Before
    public void setUp() throws IOException
    {
        config = new UConfig(Resources.toString(Resources.getResource("test.yml"), Charset.defaultCharset()));
        config.load();
    }

    @Test
    public void testGetters()
    {
        String str = config.getString("string-example");
        Character chr = config.getChar("char-example");
        Short srt = config.getShort("short-example");
        Integer in = config.getInt("int-example");
        Long lng = config.getLong("long-example");
        Float flt = config.getFloat("float-example");
        Double dbl = config.getDouble("double-example");
        Boolean bl = config.getBoolean("boolean-example");
        Object obj = config.get("object-example");

        assertEquals("Hello World", str);
        assertEquals(new Character('i'), chr);
        assertEquals(new Short((short) 666), srt);
        assertEquals(new Integer(500000), in);
        assertEquals(new Long(9223372036854775807L), lng);
        assertEquals(new Float(6.99), flt);
        assertEquals(new Double(44.54879255), dbl);
        assertEquals(true, bl);
        assertEquals("string", obj);

        assertEquals(false, config.hasKey("test"));
        assertEquals(true, config.hasKey("string-example"));
    }
}