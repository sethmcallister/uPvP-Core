package net.hcfpvp.core.framework.profile;

import net.hcfpvp.api.profiles.Profile;
import net.hcfpvp.api.profiles.core.StandardProfile;

import java.util.HashMap;

/**
 * Created by Wout on 13/05/2017.
 */
public class ProfileHandler
{
    private final HashMap<String, Class<? extends Profile>> profiles;

    public ProfileHandler()
    {
        profiles = new HashMap<>();

        profiles.put("standard", StandardProfile.class);
    }

    public HashMap<String, Class<? extends Profile>> getProfiles()
    {
        return profiles;
    }
}
