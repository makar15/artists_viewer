package com.example.makarov.musiciansviewer.parsing;

import android.content.Context;

import com.example.makarov.musiciansviewer.ArtistsApp;
import com.example.makarov.musiciansviewer.BuildConfig;
import com.example.makarov.musiciansviewer.database.models.Artist;
import com.example.makarov.musiciansviewer.network.ArtistsParser;
import com.example.makarov.musiciansviewer.network.robospice.model.ArtistsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class ArtistsRequestTest {

    private Context mContext;
    private ArtistsParser mParser;

    @Before
    public void setUp(){
        mParser = new ArtistsParser(ArtistsApp.initGson());
        mContext = ShadowApplication.getInstance().getApplicationContext();
    }

    /**
     * Парсим: /assets/artistscorrect.json
     * И проверяем : 1) два ли объекта.
     * 2) имя первой группы : Моя Мишель.
     */
    @Test
    public void testParsJsonCorrect() throws JSONException {
        JSONArray array = new JSONArray(loadJSONFromAsset("artistscorrect.json"));
        ArtistsList list = mParser.parse(array);
        List<Artist> artists = list.getArtists();
        assertEquals(2, artists.size());
        assertEquals("Моя Мишель", artists.get(0).getName());
    }

    /**
     * /assets/artistsnotcorrect.json  -  нарушил формат,
     * У 2-го JSONObject поле: cover, обернул в JSONArray.
     */
    @Test
    public void testParsJsonNotCorrect() throws JSONException {
        try {
            JSONArray array = new JSONArray(loadJSONFromAsset("artistsnotcorrect.json"));
            ArtistsList list = mParser.parse(array);
            assertNull(list);
        } catch (Exception e) {
            return; // Success test
        }

        Assert.fail();
    }

    /**
     * Из папки assets получить JSONArray строкой.
     */
    public String loadJSONFromAsset(String assetJsonPath) {
        String json;
        try {
            InputStream stream = mContext.getAssets().open(assetJsonPath);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Assert.fail();
            return null;
        }
        return json;
    }

}