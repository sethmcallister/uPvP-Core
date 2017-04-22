package us.upvp.core.data.redis.messaging;

/**
 * Created by Wout on 22/04/2017.
 */
public interface MessageHandler
{
    void handle(Message message);
}
