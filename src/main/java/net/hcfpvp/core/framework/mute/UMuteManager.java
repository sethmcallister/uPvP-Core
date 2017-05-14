package net.hcfpvp.core.framework.mute;

import net.hcfpvp.api.framework.entity.EntityId;
import net.hcfpvp.api.framework.mute.Mute;
import net.hcfpvp.api.framework.mute.MuteManager;
import net.hcfpvp.api.framework.mute.MuteType;
import net.hcfpvp.api.framework.mute.SimplifiedMuteType;
import net.hcfpvp.core.framework.data.RedisDatabaseManager;
import net.hcfpvp.core.framework.data.model.RedisMuteDao;
import net.hcfpvp.core.util.MessageUtil;

import java.util.Date;

/**
 * Created by Wout on 14/04/2017.
 */
public class UMuteManager implements MuteManager
{
    private final RedisMuteDao dao;

    public UMuteManager(RedisDatabaseManager manager)
    {
        this.dao = manager.getMuteDao();
    }

    public void addMute(Mute mute)
    {
        dao.insert((UMute) mute);
    }

    public void removeMute(Mute mute)
    {
        dao.delete((UMute) mute);
    }

    public Mute getMute(EntityId target)
    {
        return dao.find(target.getEntityIdentifier());
    }

    @Override
    public Mute createMute(EntityId s, String s1, MuteType muteType, EntityId s2)
    {
        return new UMute(s, muteType, s1, s2);
    }

    @Override
    public Mute createMute(EntityId s, String s1, MuteType muteType, EntityId s2, Date date)
    {
        return new UMute(s, muteType, s1, s2, date);
    }

    @Override
    public boolean isMuted(EntityId id)
    {
        Mute mute = getMute(id);

        return mute != null && mute.isActive();
    }

    @Override
    public String getMuteMessage(Mute mute)
    {
        String message = "";

        message += "&aOops! Seems like you are muted. ¯\\_(ツ)_/¯\n\n";

        message += MessageUtil.getReasonFormatted(mute.getReason());

        if (mute.getSimplifiedType() == SimplifiedMuteType.TEMPORARILY)
        {
            message += MessageUtil.getExpireDateFormatted(mute.getExpireDate());
        }

        message += "&aIf you think this mute was incorrect, contact us:\n\n";

        message += MessageUtil.getServerDetails();

        return message;
    }
}
