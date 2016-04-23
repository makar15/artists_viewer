package com.example.makarov.musiciansviewer;

import android.support.annotation.Nullable;

import com.example.makarov.musiciansviewer.database.StorageManager;
import com.example.makarov.musiciansviewer.database.models.Artist;
import com.example.makarov.musiciansviewer.network.robospice.ArtistsRequest;
import com.example.makarov.musiciansviewer.network.robospice.model.ArtistsList;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

public class DataManager {

    private final StorageManager mStorageManager;
    private final SpiceManager mSpiceManager;

    public interface OnLoadArtistsCallback {
        void onLoadArtists(List<Artist> list, Exception e);
    }

    public DataManager(StorageManager storageManager, SpiceManager spiceManager) {
        mStorageManager = storageManager;
        mSpiceManager = spiceManager;
    }

    /**
     * Проверяем локальное хранилище, если в хранилище данных нет, то запускаем сетевой запрос.
     */
    public void getArtistsAsync(OnLoadArtistsCallback callback) {
        List<Artist> artists = mStorageManager.getAll();
        if (artists != null && !artists.isEmpty()) {
            callback.onLoadArtists(artists, null);
            return;
        }
        getNewArtistsAsync(callback);
    }

    public void getNewArtistsAsync(OnLoadArtistsCallback callback) {
        mSpiceManager.execute(new ArtistsRequest(), null, DurationInMillis.ONE_MINUTE,
                new ArtistsRequestListener(mStorageManager, callback));
    }

    @Nullable
    public Artist getArtistById(int artistId) {
        return mStorageManager.getById(artistId);
    }

    public static class ArtistsRequestListener implements RequestListener<ArtistsList> {

        private final StorageManager mStorageManager;
        private final OnLoadArtistsCallback mCallback;

        public ArtistsRequestListener(StorageManager storageManager, OnLoadArtistsCallback callback) {
            mStorageManager = storageManager;
            mCallback = callback;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mCallback.onLoadArtists(null, spiceException);
        }

        @Override
        public void onRequestSuccess(ArtistsList list) {
            if (list != null && !list.getArtists().isEmpty()) {
                mStorageManager.saveList(list.getArtists());
                mCallback.onLoadArtists(list.getArtists(), null);
                return;
            }
            mCallback.onLoadArtists(null, new ArtistsRequestException());
        }
    }
}
