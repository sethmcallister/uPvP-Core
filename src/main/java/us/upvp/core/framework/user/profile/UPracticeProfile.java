package us.upvp.core.framework.user.profile;

import us.upvp.api.framework.user.profile.PracticeProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Wout on 14/04/2017.
 */
public class UPracticeProfile implements PracticeProfile
{
    private AtomicInteger loses;
    private AtomicInteger wins;
    private Map<String, AtomicInteger> ladderElo;
    private AtomicInteger globalElo;

    public UPracticeProfile()
    {
        loses = new AtomicInteger(0);
        wins = new AtomicInteger(0);
        ladderElo = new HashMap<>();
        globalElo = new AtomicInteger(0);
    }

    public AtomicInteger getLoses()
    {
        return loses;
    }

    public AtomicInteger getWins()
    {
        return wins;
    }

    public AtomicInteger getEloByLadder(String s)
    {
        return ladderElo.get(s);
    }

    public AtomicInteger getGlobalElo()
    {
        return globalElo;
    }

    public void setGlobalElo(AtomicInteger globalElo)
    {
        this.globalElo = globalElo;
    }

    public void setWins(AtomicInteger wins)
    {
        this.wins = wins;
    }

    public void setLoses(AtomicInteger loses)
    {
        this.loses = loses;
    }

    public Map<String, AtomicInteger> getLadderElo()
    {
        return ladderElo;
    }

    public void setLadderElo(Map<String, AtomicInteger> ladderElo)
    {
        this.ladderElo = ladderElo;
    }
}
