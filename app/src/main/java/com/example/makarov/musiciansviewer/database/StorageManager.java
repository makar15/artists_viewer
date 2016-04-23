package com.example.makarov.musiciansviewer.database;

import android.content.Context;

import com.example.makarov.musiciansviewer.database.models.Artist;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class StorageManager {

    private final Context mContext;
    private boolean mInitializedRealm = false;

    public StorageManager(Context context) {
        mContext = context;
    }

    public void saveList(List<? extends RealmObject> objects) {
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(objects);
        realm.commitTransaction();
    }

    public RealmResults<Artist> getAll() {
        return getRealm()
                .where(Artist.class)
                .findAll();
    }

    public Artist getById(int artistId) {
        return getRealm()
                .where(Artist.class)
                .equalTo(Artist.ID, artistId)
                .findFirst();
    }

    private Realm getRealm() {
        initIfNeeded(mContext);
        return Realm.getDefaultInstance();
    }

    private void initIfNeeded(Context context) {
        if (!mInitializedRealm) {
            mInitializedRealm = true;
            Realm.setDefaultConfiguration(new RealmConfiguration.Builder(context)
                    .deleteRealmIfMigrationNeeded().schemaVersion(0).build());
        }
    }
}
