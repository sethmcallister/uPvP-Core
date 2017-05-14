package net.hcfpvp.core.framework.util;

import net.hcfpvp.api.framework.util.UUIDFetcher;

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
            return new net.hcfpvp.core.framework.util.UUIDFetcher(Collections.singletonList(s)).call().get(s);
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
