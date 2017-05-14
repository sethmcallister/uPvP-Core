package net.hcfpvp.core.framework.ban;

import net.hcfpvp.api.framework.ban.Ban;
import net.hcfpvp.api.framework.ban.BanManager;
import net.hcfpvp.api.framework.ban.BanType;
import net.hcfpvp.api.framework.ban.SimplifiedBanType;
import net.hcfpvp.api.framework.entity.EntityId;
import net.hcfpvp.api.framework.user.User;
import net.hcfpvp.core.framework.data.RedisDatabaseManager;
import net.hcfpvp.core.framework.data.model.RedisBanDao;
import net.hcfpvp.core.framework.entity.UIPEntity;
import net.hcfpvp.core.util.MessageUtil;

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
        return dao.find(target.getEntityIdentifier());
    }

    @Override
    public Ban createBan(EntityId target, String reason, BanType type, EntityId bannedBy)
    {
        return new UBan(target, type, reason, bannedBy, null);
    }

    @Override
    public Ban createBan(EntityId target, String reason, BanType type, EntityId bannedBy, Date expireDate)
    {
        if (type == BanType.IP_PERMANENT || type == BanType.IP_TEMPORARILY)
        {
            if (target instanceof UIPEntity)
            {
                return new UBan(target, type, reason, bannedBy, expireDate);
            }

            target = ((User) target).getMostRecentIP();
        }

        return new UBan(target, type, reason, bannedBy, expireDate);
    }

    @Override
    public boolean isBanned(EntityId id)
    {
        Ban ban = getBan(id);

        return ban != null && ban.isActive();
    }

    @Override
    public String getBanMessage(Ban ban)
    {
        String message = "";

        message += "&aOh no! What happened? Seems like you have been banned. ¯\\_(ツ)_/¯\n\n";

        message += MessageUtil.getReasonFormatted(ban.getReason());

        if (ban.getSimplifiedType() == SimplifiedBanType.TEMPORARILY)
        {
            message += MessageUtil.getExpireDateFormatted(ban.getExpireDate());
        }

        message += "&aIf you think this ban was incorrect, contact us:\n\n";

        message += MessageUtil.getServerDetails();

        return message;
    }
}
