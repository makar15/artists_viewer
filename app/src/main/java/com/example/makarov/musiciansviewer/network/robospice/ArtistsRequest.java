package com.example.makarov.musiciansviewer.network.robospice;

import com.example.makarov.musiciansviewer.ArtistsApp;
import com.example.makarov.musiciansviewer.network.ArtistsParser;
import com.example.makarov.musiciansviewer.network.okhttp.API;
import com.example.makarov.musiciansviewer.network.robospice.model.ArtistsList;
import com.example.makarov.musiciansviewer.utils.Constant;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;

public class ArtistsRequest extends SpringAndroidSpiceRequest<ArtistsList> {

    public ArtistsRequest() {
        super(ArtistsList.class);
    }

    @Override
    public ArtistsList loadDataFromNetwork() throws JSONException, IOException {
        String response = API.getResponse(Constant.ARTISTS_LIST_URL);
        JSONArray jsonArray = (JSONArray) new JSONTokener(response).nextValue();
        ArtistsParser parser = ArtistsApp.getApp().getArtistsParser();
        return parser.parse(jsonArray);
    }
}
