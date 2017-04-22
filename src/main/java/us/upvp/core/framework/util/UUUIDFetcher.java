package us.upvp.core.framework.util;

import us.upvp.api.framework.util.UUIDFetcher;

import java.util.Collections;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class UUUIDFetcher implements UUIDFetcher
{
    public UUID findByUsername(String s)
    {
        try
        {
            return new us.upvp.core.framework.util.UUIDFetcher(Collections.singletonList(s)).call().get(s);
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
