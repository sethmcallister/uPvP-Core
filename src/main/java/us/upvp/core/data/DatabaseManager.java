package us.upvp.core.data;

import us.upvp.core.data.model.BanDao;
import us.upvp.core.data.model.MuteDao;
import us.upvp.core.data.model.UserDao;

/**
 * Created by Wout on 15/04/2017.
 */
public interface DatabaseManager
{
    BanDao getBanDao();

    MuteDao getMuteDao();

    UserDao getUserDao();
}
