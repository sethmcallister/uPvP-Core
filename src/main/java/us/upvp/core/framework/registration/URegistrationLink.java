package us.upvp.core.framework.registration;

import us.upvp.api.API;
import us.upvp.api.framework.registration.RegistrationLink;
import us.upvp.api.framework.user.User;

import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class URegistrationLink implements RegistrationLink
{
    private final UUID uniqueId;
    private boolean active;

    public URegistrationLink(UUID uniqueId, boolean active)
    {
        this.uniqueId = uniqueId;
        this.active = active;
    }

    public UUID getUniqueId()
    {
        return uniqueId;
    }

    public Boolean isActive()
    {
        return active;
    }

    public void activate(String s)
    {
        active = false;

        User user = API.getUserManager().findOfflineByUniqueId(uniqueId);

        user.setPassword(s);

        // TODO safe user or ?
    }
}
