package us.upvp.core.framework.time;

import us.upvp.api.framework.time.TimeFormatter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Wout on 14/04/2017.
 */
public class UTimeFormatter implements TimeFormatter
{
    private final DateTimeFormatter formatter;

    public UTimeFormatter()
    {
        formatter = DateTimeFormatter.ofPattern("d MMMM YYYY, HH:mm");
    }

    public String getFormatted(long l)
    {
        return formatter.format(new Date(l).toInstant().atZone(ZoneId.of("UTC")));
    }

    public long getTimeFromString(String a)
    {
        if (a.endsWith("s"))
        {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 1000L;
        }
        if (a.endsWith("m"))
        {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 60000L;
        }
        if (a.endsWith("h"))
        {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 3600000L;
        }
        if (a.endsWith("d"))
        {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 86400000L;
        }
        if (a.endsWith("m"))
        {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 2592000000L;
        }
        if (a.endsWith("y"))
        {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 31104000000L;
        }

        return -1L;
    }
}
