package us.upvp.core.framework.server.practice;

import us.upvp.api.framework.server.practice.EloEntry;
import us.upvp.api.framework.server.practice.Ladder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Wout on 14/04/2017.
 */
public class UEloEntry implements EloEntry
{
    private final Ladder ladder;
    private AtomicInteger elo;

    public UEloEntry(Ladder ladder, AtomicInteger elo)
    {
        this.ladder = ladder;
        this.elo = elo;
    }

    public Ladder getLadder()
    {
        return ladder;
    }

    public AtomicInteger getElo()
    {
        return elo;
    }

    public void setElo(Integer amount)
    {
        elo.set(amount);
    }
}
