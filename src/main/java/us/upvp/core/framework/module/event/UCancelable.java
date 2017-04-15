package us.upvp.core.framework.module.event;

import us.upvp.api.framework.module.event.Cancelable;

/**
 * Created by Wout on 14/04/2017.
 */
public class UCancelable implements Cancelable
{
    private boolean isCancelled;

    public boolean isCancelled()
    {
        return isCancelled;
    }

    public void setCancelled(boolean b)
    {
        isCancelled = b;
    }
}
