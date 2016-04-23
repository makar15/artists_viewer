package com.example.makarov.musiciansviewer;

import android.app.Application;

import com.example.makarov.musiciansviewer.database.StorageManager;
import com.example.makarov.musiciansviewer.database.models.Cover;
import com.example.makarov.musiciansviewer.database.models.Genre;
import com.example.makarov.musiciansviewer.network.ArtistsParser;
import com.example.makarov.musiciansviewer.network.gson.CoverDeserializer;
import com.example.makarov.musiciansviewer.network.gson.GenresTypeAdapter;
import com.example.makarov.musiciansviewer.utils.SystemUtils;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.octo.android.robospice.GsonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ArtistsApp extends Application {

    private static ArtistsApp mArtistsApp;

    private SpiceManager mSpiceManager;
    private StorageManager mStorageManager;
    private DataManager mDataManager;
    private ArtistsParser mArtistsParser;

    @Override
    public void onCreate() {
        super.onCreate();
        mArtistsApp = this;
        Gson gson = initGson();

        mSpiceManager = new SpiceManager(GsonSpringAndroidSpiceService.class);
        mStorageManager = new StorageManager(this);
        mDataManager = new DataManager(mStorageManager, mSpiceManager);
        mArtistsParser = new ArtistsParser(gson);
        mSpiceManager.start(this);

        massageLackOfAccessNetwork();
    }

    /**
     * Инициализируем gson и сетим :
     * 1) адаптер для чтения жанров из JSONArray.
     * 2) адаптер десериализации обложек исполнителя.
     */
    public static Gson initGson() {
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(new TypeToken<RealmList<Genre>>() {
                }.getType(), new GenresTypeAdapter())
                .registerTypeAdapter(Cover.class, new CoverDeserializer())
                .create();
    }

    private void massageLackOfAccessNetwork() {
        if (!SystemUtils.isConnectingToInternet(this)) {
            SystemUtils.toastMassage(this,
                    this.getString(R.string.start_app_please_connect_to_internet));
        }
    }

    public static ArtistsApp getApp() {
        return mArtistsApp;
    }

    public StorageManager getStorageManager() {
        return mStorageManager;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public ArtistsParser getArtistsParser() {
        return mArtistsParser;
    }

    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

}
