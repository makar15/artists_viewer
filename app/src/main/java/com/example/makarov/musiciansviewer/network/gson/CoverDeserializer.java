package com.example.makarov.musiciansviewer.network.gson;

import com.example.makarov.musiciansviewer.database.models.Cover;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CoverDeserializer implements JsonDeserializer<Cover> {

    @Override
    public Cover deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();

        final Cover cover = new Cover();
        cover.setCoverSmallUrl(jsonObject.get(Cover.COVER_SMALL).getAsString());
        cover.setCoverBigUrl(jsonObject.get(Cover.COVER_BIG).getAsString());
        return cover;
    }
}
