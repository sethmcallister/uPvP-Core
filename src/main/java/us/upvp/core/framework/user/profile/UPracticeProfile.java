package us.upvp.core.framework.user.profile;

import us.upvp.api.framework.server.practice.Ladder;
import us.upvp.api.framework.user.profile.PracticeProfile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Wout on 14/04/2017.
 */
public class UPracticeProfile implements PracticeProfile
{
    public AtomicInteger getLoses()
    {
        return null;
    }

    public AtomicInteger getWins()
    {
        return null;
    }

    public AtomicInteger getEloByLadder(Ladder ladder)
    {
        return null;
    }

    public AtomicInteger getEloByLadder(String s)
    {
        return null;
    }

    public AtomicInteger getGlobalElo()
    {
        return null;
    }

    public void setLadderElo(Ladder ladder, Integer integer)
    {

    }
}
