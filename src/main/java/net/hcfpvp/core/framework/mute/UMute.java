package net.hcfpvp.core.framework.mute;

import net.hcfpvp.api.framework.entity.EntityId;
import net.hcfpvp.api.framework.mute.Mute;
import net.hcfpvp.api.framework.mute.MuteType;
import net.hcfpvp.api.framework.mute.SimplifiedMuteType;

import java.util.Date;

/**
 * Created by Wout on 14/04/2017.
 */
public class UMute implements Mute
{
    private final MuteType type;
    private final SimplifiedMuteType simplifiedMuteType;
    private final String reason;
    private final EntityId mutedBy;
    private final Date expireDate;
    private final Date muteDate;
    private final EntityId target;

    public UMute(EntityId target, MuteType type, String reason, EntityId mutedBy)
    {
        this.target = target;
        this.simplifiedMuteType = (type == MuteType.IP_PERMANENT || type ==
                                                                    MuteType.NORMAL_PERMANENT ? SimplifiedMuteType.PERMANENT : SimplifiedMuteType.TEMPORARILY);
        this.type = type;
        this.reason = reason;
        this.mutedBy = mutedBy;
        this.expireDate = new Date();
        this.muteDate = new Date();
    }

    public UMute(EntityId target, MuteType type, String reason, EntityId mutedBy, Date expireDate)
    {
        this.target = target;
        this.simplifiedMuteType = (type == MuteType.IP_PERMANENT || type ==
                                                                    MuteType.NORMAL_PERMANENT ? SimplifiedMuteType.PERMANENT : SimplifiedMuteType.TEMPORARILY);
        this.type = type;
        this.reason = reason;
        this.mutedBy = mutedBy;
        this.expireDate = expireDate;
        this.muteDate = new Date();
    }


    public MuteType getType()
    {
        return type;
    }

    public SimplifiedMuteType getSimplifiedType()
    {
        return simplifiedMuteType;
    }

    public String getReason()
    {
        return reason;
    }

    public EntityId getMutedBy()
    {
        return mutedBy;
    }

    public Date getExpireDate()
    {
        return expireDate;
    }

    public Date getMuteDate()
    {
        return muteDate;
    }

    public EntityId getTarget()
    {
        return target;
    }

    public boolean isActive()
    {
        return simplifiedMuteType == SimplifiedMuteType.PERMANENT ||
               System.currentTimeMillis() < getExpireDate().getTime();
    }
}
