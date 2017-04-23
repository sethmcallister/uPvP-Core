package us.upvp.core.framework.ban;

import us.upvp.api.framework.ban.Ban;
import us.upvp.api.framework.ban.BanManager;
import us.upvp.api.framework.ban.BanType;
import us.upvp.api.framework.entity.EntityId;
import us.upvp.api.framework.user.OfflineUser;
import us.upvp.core.framework.data.RedisDatabaseManager;
import us.upvp.core.framework.data.model.RedisBanDao;
import us.upvp.core.framework.entity.UIPEntity;

import java.util.Date;

/**
 * Created by Wout on 14/04/2017.
 */
public class UBanManager implements BanManager
{
    private final RedisBanDao dao;

    public UBanManager(RedisDatabaseManager manager)
    {
        this.dao = manager.getBanDao();
    }

    public void addBan(Ban ban)
    {
        dao.insert((UBan) ban);
    }

    public void removeBan(Ban b)
    {
        dao.delete((UBan) b);
    }

    public Ban getBan(EntityId target)
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
    public Ban createBan(EntityId target, String reason, BanType type, EntityId bannedBy)
    {
        return new UBan(target, type, reason, bannedBy);
    }

    @Override
    public Ban createBan(EntityId target, String reason, BanType type, EntityId bannedBy, Date expireDate)
    {
        return new UBan(target, type, reason, bannedBy, expireDate);
    }
}
