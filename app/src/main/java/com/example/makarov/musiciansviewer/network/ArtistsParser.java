package com.example.makarov.musiciansviewer.network;

import com.example.makarov.musiciansviewer.database.models.Artist;
import com.example.makarov.musiciansviewer.network.robospice.model.ArtistsList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ArtistsParser {

    private final Gson mGson;

    public ArtistsParser(Gson gson) {
        mGson = gson;
    }

    public ArtistsList parse(JSONArray jsonArray) throws JSONException {

        ArtistsList list = new ArtistsList();
        List<Artist> artists = list.getArtists();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String jsonText = jsonObject.toString();
            Artist artist = mGson.fromJson(jsonText, Artist.class);

            artists.add(artist);
        }
        return list;
    }
}
