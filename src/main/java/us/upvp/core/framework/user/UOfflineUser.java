package us.upvp.core.framework.user;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import us.upvp.api.API;
import us.upvp.api.framework.permission.Group;
import us.upvp.api.framework.permission.Rank;
import us.upvp.api.framework.user.OfflineUser;
import us.upvp.api.framework.user.profile.HCFProfile;
import us.upvp.api.framework.user.profile.PracticeProfile;
import us.upvp.core.data.redis.RedisDatabaseManager;
import us.upvp.core.data.redis.messaging.Message;
import us.upvp.core.data.redis.messaging.MessageType;
import us.upvp.core.framework.permission.URank;
import us.upvp.core.framework.user.profile.UHCFProfile;
import us.upvp.core.framework.user.profile.UPracticeProfile;

import java.util.List;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class UOfflineUser implements OfflineUser
{
    private final UUID uniqueId;
    private String lastName;
    private List<URank> ranks;
    private UPracticeProfile practiceProfile;
    private UHCFProfile hcfProfile;
    private List<String> ips;
    private List<UUID> ignoreList;
    private String joinDate;
    private String password;

    public UOfflineUser(UUID uniqueId)
    {
        this.uniqueId = uniqueId;
        this.ranks = Lists.newArrayList();
    }

    public UOfflineUser(UUID uniqueId, String lastName, List<URank> ranks, PracticeProfile practiceProfile, HCFProfile hcfProfile, List<String> ips, List<UUID> ignoreList, String joinDate, String password)
    {
        this.uniqueId = uniqueId;
        this.lastName = lastName;
        this.ranks = ranks;
        this.practiceProfile = (UPracticeProfile) practiceProfile;
        this.hcfProfile = (UHCFProfile) hcfProfile;
        this.ips = ips;
        this.ignoreList = ignoreList;
        this.joinDate = joinDate;
        this.password = password;
    }

    @Override
    public UUID getUniqueId()
    {
        return uniqueId;
    }

    @Override
    public String getLastName()
    {
        return lastName;
    }

    @Override
    public void setLastName(String s)
    {
        this.lastName = s;
        update();
    }

    @Override
    public Rank getRank()
    {
        return ranks.stream()
                    .filter(r -> API.getServer().getName().equalsIgnoreCase(r.getApplicableServer()))
                    .findAny()
                    .orElse(ranks.stream()
                                 .filter(r -> r.getApplicableServer().equals("___GLOBAL___"))
                                 .findAny()
                                 .orElse(null));
    }

    @Override
    public Group getGroup()
    {
        return getRank() == null ? Group.MEMBER : getRank().getGroup();
    }

    @Override
    public PracticeProfile getPractice()
    {
        return practiceProfile;
    }

    @Override
    public void setPractice(PracticeProfile practiceProfile)
    {
        this.practiceProfile = (UPracticeProfile) practiceProfile;
        update();
    }

    @Override
    public HCFProfile getHCFactions()
    {
        return hcfProfile;
    }

    @Override
    public List<String> getAllIPs()
    {
        return ips;
    }

    @Override
    public void addIP(String s)
    {
        ips.add(s);
        update();
    }

    @Override
    public List<UUID> getIgnoredList()
    {
        return ignoreList;
    }

    @Override
    public String getJoinedDate()
    {
        return joinDate;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public void setPassword(String s)
    {
        this.password = s;
        update();
    }

    @Override
    public void setRank(Group group, String s)
    {
        URank r = ranks.stream().filter(ra -> ra.getApplicableServer().equalsIgnoreCase(s)).findAny().orElse(null);

        if (r != null)
        {
            r.setGroup(group);
        }
        else
        {
            ranks.add(new URank(group, s));
        }

        update();
    }

    public List<URank> getRanks()
    {
        return ranks;
    }

    private void update()
    {
        UUserManager manager = (UUserManager) API.getUserManager();

        manager.getDao().update((UUser) this);

        ((RedisDatabaseManager) manager.getDatabaseManager()).getClient().connectPubSub().sync().publish("update", new GsonBuilder().create().toJson(new Message(uniqueId,
                                                                                                                                                   MessageType.USER)));
    }

    public void setPracticeProfile(PracticeProfile practiceProfile)
    {
        this.practiceProfile = (UPracticeProfile) practiceProfile;
    }

    public void setHcfProfile(HCFProfile hcfProfile)
    {
        this.hcfProfile = (UHCFProfile) hcfProfile;
    }

    public void setIps(List<String> ips)
    {
        this.ips = ips;
    }

    public void setIgnoreList(List<UUID> ignoreList)
    {
        this.ignoreList = ignoreList;
    }

    public void setJoinDate(String joinDate)
    {
        this.joinDate = joinDate;
    }

    @Override
    public String toString()
    {
        return "UOfflineUser{" +
               "uniqueId=" + uniqueId +
               ", lastName='" + lastName + '\'' +
               ", ranks=" + ranks +
               ", practiceProfile=" + practiceProfile +
               ", hcfProfile=" + hcfProfile +
               ", ips=" + ips +
               ", ignoreList=" + ignoreList +
               ", joinDate='" + joinDate + '\'' +
               ", password='" + password + '\'' +
               '}';
    }

    @Override
    public String getEntityIdentifier()
    {
        return String.format("Type: %s, UUID: %s", "OfflineUser", getUniqueId().toString());
    }
}
