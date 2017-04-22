package us.upvp.core.data.redis.messaging;

import java.util.UUID;

/**
 * Created by Wout on 22/04/2017.
 */
public class Message
{
    private UUID id;
    private MessageType type;

    public Message(UUID id, MessageType type)
    {
        this.id = id;
        this.type = type;
    }

    public UUID getId()
    {
        return id;
    }

    public MessageType getType()
    {
        return type;
    }
}
