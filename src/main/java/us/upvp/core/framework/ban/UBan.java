package us.upvp.core.framework.ban;

import us.upvp.api.framework.ban.Ban;
import us.upvp.api.framework.ban.BanType;
import us.upvp.api.framework.ban.SimplifiedBanType;
import us.upvp.api.framework.entity.EntityId;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2017.
 */
public class UBan implements Ban
{
    private final UUID id = UUID.randomUUID();
    private final BanType type;
    private final SimplifiedBanType simplifiedBanType;
    private final String reason;
    private final EntityId bannedBy;
    private final Date expireDate;
    private final Date banDate;
    private final EntityId target;
    private boolean active;

    public UBan(EntityId target, BanType type, String reason, EntityId bannedBy)
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

    public UBan(EntityId target, BanType type, String reason, EntityId bannedBy, Date expireDate)
    {
        this.target = target;
        this.type = type;
        this.active = true;
        this.simplifiedBanType = (
                type == BanType.BLACKLIST ? SimplifiedBanType.BLACKLIST : (type == BanType.IP_PERMANENT || type ==
                                                                                                           BanType.NORMAL_PERMANENT) ? SimplifiedBanType.PERMANENT : SimplifiedBanType.TEMPORARILY);
        this.reason = reason;
        this.bannedBy = bannedBy;
        this.expireDate = expireDate;
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

    public EntityId getBannedBy()
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

    public EntityId getTarget()
    {
        return target;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
