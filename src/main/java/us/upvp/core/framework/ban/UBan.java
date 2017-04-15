package us.upvp.core.framework.ban;

import us.upvp.api.framework.ban.Ban;
import us.upvp.api.framework.ban.BanType;
import us.upvp.api.framework.ban.SimplifiedBanType;

import java.util.Date;

/**
 * Created by Wout on 14/04/2017.
 */
public class UBan implements Ban
{
    private final BanType type;
    private final SimplifiedBanType simplifiedBanType;
    private final String reason;
    private final String bannedBy;
    private final Date expireDate;
    private final Date banDate;
    private final String target;
    private final boolean active;

    public UBan(String target, BanType type, String reason, String bannedBy)
    {
        this.target = target;
        this.type = type;
        this.active = true;
        this.simplifiedBanType = (
                type == BanType.BLACKLIST ? SimplifiedBanType.BLACKLIST : (type == BanType.IP_PERMANENT || type ==
                                                                                                           BanType.NORMAL_PERMANENT) ? SimplifiedBanType.PERMANENT : SimplifiedBanType.TEMPORARILY);
        this.reason = reason;
        this.bannedBy = bannedBy;
        this.expireDate = new Date();
        this.banDate = new Date();
    }

    public BanType getType()
    {
        return type;
    }

    public SimplifiedBanType getSimplifiedType()
    {
        return simplifiedBanType;
    }

    public String getReason()
    {
        return reason;
    }

    public String getBannedBy()
    {
        return bannedBy;
    }

    public Date getExpireDate()
    {
        return expireDate;
    }

    public Date getBanDate()
    {
        return banDate;
    }

    public String getTarget()
    {
        return target;
    }

    public boolean isActive()
    {
        return active;
    }
}
