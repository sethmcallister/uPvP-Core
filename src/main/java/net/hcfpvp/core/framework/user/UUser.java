package net.hcfpvp.core.framework.user;

import com.google.common.collect.Sets;
import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.module.command.CommandCaller;
import net.hcfpvp.api.framework.permission.Group;
import net.hcfpvp.api.framework.user.OfflineUser;
import net.hcfpvp.api.framework.user.User;
import net.hcfpvp.api.framework.user.profile.StandardProfileKey;
import net.hcfpvp.api.profiles.Profile;
import net.hcfpvp.core.framework.permission.URank;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class UUser extends UOfflineUser implements User, OfflineUser, CommandCaller
{
    public UUser(UUID uniqueId)
    {
        super(uniqueId);

        getStandardProfile().getValues()
                            .put(StandardProfileKey.RANKS.getKey(),
                                 Sets.newHashSet(new URank(Group.MEMBER, "___GLOBAL___")));
    }

    public UUser(UUID uniqueId, Collection<Profile> profiles)
    {
        super(uniqueId, profiles);
    }

    @Override
    public void kick(String s)
    {
        API.getPlugin().kick(this, s);
    }

    @Override
    public void sendMessage(String s)
    {
        API.getPlugin().sendMessage(this, s);
    }

    @Override
    public void sendUnformattedMessage(String s)
    {
        API.getPlugin().sendMessage(this, s);
    }
}