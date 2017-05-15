package net.hcfpvp.core.framework.data.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.entity.EntityId;
import net.hcfpvp.api.framework.user.OfflineUser;
import net.hcfpvp.core.framework.console.UConsoleUser;
import net.hcfpvp.core.framework.entity.UIPEntity;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Wout on 23/04/2017.
 */
public class EntityIdAdapter extends TypeAdapter<EntityId>
{
    @Override
    public void write(JsonWriter writer, EntityId id) throws IOException
    {
        writer.value(id.getEntityIdentifier());
    }

    @Override
    public EntityId read(JsonReader reader) throws IOException
    {
        String[] id = reader.nextString().split(Pattern.quote("|"));

        if (id[0].equalsIgnoreCase("USER"))
        {
            OfflineUser user = API.getUserManager().findByUniqueId(UUID.fromString(id[1]));

            if (user != null)
            {
                return user;
            }
            else
            {
                return API.getUserManager().findOfflineByUniqueId(UUID.fromString(id[1]));
            }
        }
        else if (id[0].equalsIgnoreCase("CONSOLE"))
        {
            return new UConsoleUser(id[1]);
        }
        else
        {
            return new UIPEntity(id[1]);
        }
    }
}
