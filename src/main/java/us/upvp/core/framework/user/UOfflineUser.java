package us.upvp.core.framework.user;

import us.upvp.api.framework.permission.Group;
import us.upvp.api.framework.permission.Rank;
import us.upvp.api.framework.user.OfflineUser;
import us.upvp.api.framework.user.profile.HCFProfile;
import us.upvp.api.framework.user.profile.PracticeProfile;

import java.util.List;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class UOfflineUser implements OfflineUser
{
    public UUID getUniqueId()
    {
        return null;
    }

    public String getLastName()
    {
        return null;
    }

    public void setLastName(String s)
    {

    }

    public Rank getRank()
    {
        return null;
    }

    public void setRank(Rank rank)
    {

    }

    public Group getGroup()
    {
        return null;
    }

    public PracticeProfile getPractice()
    {
        return null;
    }

    public void setPractice(PracticeProfile practiceProfile)
    {

    }

    public HCFProfile getHCFactions()
    {
        return null;
    }

    public List<String> getAllIPs()
    {
        return null;
    }

    public void addIP(String s)
    {

    }

    public List<UUID> getIgnoredList()
    {
        return null;
    }

    public String getJoinedDate()
    {
        return null;
    }

    public String getPassword()
    {
        return null;
    }

    public void setPassword(String s)
    {

    }
}
