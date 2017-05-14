package net.hcfpvp.core.framework.ban;

import net.hcfpvp.api.framework.ban.Ban;
import net.hcfpvp.api.framework.ban.BanType;
import net.hcfpvp.api.framework.ban.SimplifiedBanType;
import net.hcfpvp.api.framework.entity.EntityId;

import java.util.Date;

/**
 * Created by Wout on 14/04/2017.
 */
public class UBan implements Ban
{
    private final BanType type;
    private final SimplifiedBanType simplifiedBanType;
    private final String reason;
    private final EntityId bannedBy;
    private final Date expireDate;
    private final Date banDate;
    private final EntityId target;

    public UBan(EntityId target, BanType type, String reason, EntityId bannedBy)
    {
        this.target = target;
        this.type = type;
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
        return simplifiedBanType == SimplifiedBanType.PERMANENT ||
               System.currentTimeMillis() < getExpireDate().getTime();
    }
}
