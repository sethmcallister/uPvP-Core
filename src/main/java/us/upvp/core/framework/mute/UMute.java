package us.upvp.core.framework.mute;

import us.upvp.api.framework.mute.Mute;
import us.upvp.api.framework.mute.MuteType;
import us.upvp.api.framework.mute.SimplifiedMuteType;

import java.util.Date;

/**
 * Created by Wout on 14/04/2017.
 */
public class UMute implements Mute
{
    private final MuteType type;
    private final SimplifiedMuteType simplifiedMuteType;
    private final String reason;
    private final String mutedBy;
    private final Date expireDate;
    private final Date muteDate;
    private final String target;
    private final boolean active;

    public UMute(String target, MuteType type, String reason, String mutedBy)
    {
        this.target = target;
        this.simplifiedMuteType = (type == MuteType.IP_PERMANENT || type == MuteType.NORMAL_PERMANENT ? SimplifiedMuteType.PERMANENT : SimplifiedMuteType.TEMPORARILY);
        this.active = true;
        this.type = type;
        this.reason = reason;
        this.mutedBy = mutedBy;
        this.expireDate = new Date();
        this.muteDate = new Date();
    }

    public UMute(String target, MuteType type, String reason, String mutedBy, Date expireDate)
    {
        this.target = target;
        this.simplifiedMuteType = (type == MuteType.IP_PERMANENT || type == MuteType.NORMAL_PERMANENT ? SimplifiedMuteType.PERMANENT : SimplifiedMuteType.TEMPORARILY);
        this.active = true;
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

    public String getMutedBy()
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

    public String getTarget()
    {
        return target;
    }

    public boolean isActive()
    {
        return active;
    }
}
