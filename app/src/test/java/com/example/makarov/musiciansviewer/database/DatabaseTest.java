package com.example.makarov.musiciansviewer.database;

import android.content.Context;
import android.test.AndroidTestCase;

import com.example.makarov.musiciansviewer.BuildConfig;
import com.example.makarov.musiciansviewer.database.models.Artist;
import com.example.makarov.musiciansviewer.database.models.Cover;
import com.example.makarov.musiciansviewer.database.models.Genre;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.List;

import io.realm.RealmList;

/**
 * Тесты для рилма не заработали.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class DatabaseTest extends AndroidTestCase {

    private StorageManager mStorageManager;
    private Context mContext;

    @Before
    public void setUp() {
        mContext = ShadowApplication.getInstance().getApplicationContext();
        mStorageManager = new StorageManager(mContext);

    }

    /**
     * Сохраняем в БД 2-х артистов и запоминаем количество артистов в БД,
     * далее сохраняем повторно тех же артистов и проверяем: не увеличилось ли количество
     * артистов в БД.
     */
    @Test
    public void testDatabaseQuery() {

        List<Artist> artists = new RealmList<>();

        Artist artistFirst = new Artist(111111, "test", "test", "test",
                new RealmList<Genre>(), new Cover(), 10, 10);
        Artist artistSecond = new Artist(222222, "test", "test", "test",
                new RealmList<Genre>(), new Cover(), 20, 20);

        artists.add(artistFirst);
        artists.add(artistSecond);

        int databaseSize = mStorageManager.getAll().size();
        mStorageManager.saveList(artists);
        int databaseNewSize = mStorageManager.getAll().size();
        assertEquals(databaseSize + artists.size(), databaseNewSize);

        mStorageManager.saveList(artists);
        assertEquals(databaseSize + artists.size(), databaseNewSize);
    }
}