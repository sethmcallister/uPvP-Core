package us.upvp.core.framework.mute;

import us.upvp.api.framework.entity.EntityId;
import us.upvp.api.framework.mute.Mute;
import us.upvp.api.framework.mute.MuteManager;
import us.upvp.api.framework.mute.MuteType;
import us.upvp.api.framework.user.OfflineUser;
import us.upvp.core.data.DatabaseManager;
import us.upvp.core.data.model.MuteDao;
import us.upvp.core.framework.entity.UIPEntity;

import java.util.Date;

/**
 * Created by Wout on 14/04/2017.
 */
public class UMuteManager implements MuteManager
{
    private final MuteDao dao;

    public UMuteManager(DatabaseManager manager)
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
        if (target instanceof OfflineUser)
        {
            return dao.find(((OfflineUser) target).getUniqueId().toString());
        }
        else
        {
            return dao.find(((UIPEntity) target).getIp());
        }
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
}
