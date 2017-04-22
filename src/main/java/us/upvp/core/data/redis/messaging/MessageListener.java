package us.upvp.core.data.redis.messaging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambdaworks.redis.pubsub.RedisPubSubListener;

/**
 * Created by Wout on 22/04/2017.
 */
public class MessageListener implements RedisPubSubListener<String, String>
{
    private final MessageType type;
    private final MessageHandler handler;
    private final Gson gson;

    public MessageListener(MessageType type, MessageHandler handler)
    {
        this.type = type;
        this.handler = handler;
        this.gson = new GsonBuilder().create();
    }

    @Override
    public void message(String channel, String message)
    {
        if (channel.equals("update"))
        {
            Message msg = gson.fromJson(message, Message.class);

            if (msg.getType() == type)
            {
                handler.handle(msg);
            }
        }
    }

    @Override
    public void message(String s, String k1, String s2)
    {

    }

    @Override
    public void subscribed(String s, long l)
    {

    }

    @Override
    public void psubscribed(String s, long l)
    {

    }

    @Override
    public void unsubscribed(String s, long l)
    {

    }

    @Override
    public void punsubscribed(String s, long l)
    {

    }
}
