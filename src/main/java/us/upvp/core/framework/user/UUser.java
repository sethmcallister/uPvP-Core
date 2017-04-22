package us.upvp.core.framework.user;

import us.upvp.api.API;
import us.upvp.api.framework.module.command.CommandCaller;
import us.upvp.api.framework.server.NativeFunctionality;
import us.upvp.api.framework.user.OfflineUser;
import us.upvp.api.framework.user.User;
import us.upvp.api.framework.user.profile.HCFProfile;
import us.upvp.api.framework.user.profile.PracticeProfile;
import us.upvp.core.framework.permission.URank;

import java.util.List;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class UUser extends UOfflineUser implements User, OfflineUser, CommandCaller
{
    private boolean messagesToggled = false;
    private boolean inStaffMode = false;
    private boolean isVanished = false;
    private boolean isInStaffChat = false;

    public UUser()
    {
        super(UUID.randomUUID());
    }

    public UUser(UUID uniqueId, String lastName, List<URank> rank, PracticeProfile practiceProfile, HCFProfile hcfProfile, List<String> ips, List<UUID> ignoreList, String joinDate, String password)
    {
        super(uniqueId, lastName, rank, practiceProfile, hcfProfile, ips, ignoreList, joinDate, password);
    }

    @Override
    public boolean toggledMessages()
    {
        return messagesToggled;
    }

    @Override
    public void setToggledMessages(boolean b)
    {
        this.messagesToggled = b;
    }

    @Override
    public boolean inStaffMode()
    {
        return inStaffMode;
    }

    @Override
    public void setStaffMode(boolean b)
    {
        this.inStaffMode = b;
    }

    @Override
    public boolean isVanished()
    {
        return isVanished;
    }

    @Override
    public void setVanished(boolean b)
    {
        this.isVanished = b;
    }

    @Override
    public boolean isStaffChat()
    {
        return isInStaffChat;
    }

    @Override
    public void setStaffChat(boolean b)
    {
        isInStaffChat = b;
    }

    /*
    TODO
     */
    @Override
    public void sendMessage(String s)
    {
        ((NativeFunctionality) API.getPlugin()).sendMessage(this, s);
    }

    @Override
    public void sendUnformattedMessage(String s)
    {
        ((NativeFunctionality) API.getPlugin()).sendMessage(this, s);
    }

    public void setMessagesToggled(boolean messagesToggled)
    {
        this.messagesToggled = messagesToggled;
    }

    public void setInStaffMode(boolean inStaffMode)
    {
        this.inStaffMode = inStaffMode;
    }

    public void setInStaffChat(boolean inStaffChat)
    {
        isInStaffChat = inStaffChat;
    }
}
