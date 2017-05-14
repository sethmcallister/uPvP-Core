package net.hcfpvp.core.framework.data.adapter;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.hcfpvp.api.framework.permission.Rank;
import net.hcfpvp.core.framework.permission.URank;

import java.io.IOException;

/**
 * Created by Wout on 14/05/2017.
 */
public class RankAdapter extends TypeAdapter<Rank>
{
    @Override
    public void write(JsonWriter jsonWriter, Rank rank) throws IOException
    {
        new GsonBuilder().create().toJson(rank, URank.class, jsonWriter);
    }

    @Override
    public Rank read(JsonReader jsonReader) throws IOException
    {
        return new GsonBuilder().create().fromJson(jsonReader, URank.class);
    }
}
