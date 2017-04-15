package us.upvp.core.data;

import us.upvp.core.framework.ban.UBan;
import us.upvp.core.framework.mute.UMute;
import us.upvp.core.framework.server.hcfactions.UFaction;
import us.upvp.core.framework.user.UUser;

/**
 * Created by Wout on 14/04/2017.
 */
public class DatabaseManager
{
    private DataDriver<UUser> userDataDriver;
    private DataDriver<UBan> banDataDriver;
    private DataDriver<UMute> muteDataDriver;
    private DataDriver<UFaction> factionDataDriver;

    public DatabaseManager()
    {

    }
}
