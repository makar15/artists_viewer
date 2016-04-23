package com.example.makarov.musiciansviewer.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makarov.musiciansviewer.ArtistsApp;
import com.example.makarov.musiciansviewer.DataManager;
import com.example.makarov.musiciansviewer.R;
import com.example.makarov.musiciansviewer.database.PicassoBigCache;
import com.example.makarov.musiciansviewer.database.models.Artist;
import com.example.makarov.musiciansviewer.database.models.Genre;
import com.example.makarov.musiciansviewer.ui.activity.MainActivity;
import com.example.makarov.musiciansviewer.utils.SystemUtils;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.squareup.picasso.Callback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArtistDetailFragment extends Fragment {
    private static final String TAG = "ArtistDetailFragment";

    @Bind(R.id.image)
    ImageView mCover;
    @Bind(R.id.genres)
    TextView mGenres;
    @Bind(R.id.data_repertoire)
    TextView mDataRepertoire;
    @Bind(R.id.description)
    TextView mDescription;
    @Bind(R.id.link)
    TextView mLink;
    @Bind(R.id.progress_load)
    CircularProgressView mProgress;

    private final DataManager mDataManager = ArtistsApp.getApp().getDataManager();

    private final Callback mLoadImageCallback = new Callback() {
        @Override
        public void onSuccess() {
            stopProgressBar();
        }

        @Override
        public void onError() {
            stopProgressBar();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.artist_detail_fragment, null);
        ButterKnife.bind(this, v);

        if (!SystemUtils.isConnectingToInternet(getContext())) {
            SystemUtils.toastMassage(getContext(),
                    getContext().getString(R.string.check_connect_to_internet));
        }

        Bundle args = getArguments();
        if (args == null || !args.containsKey(MainActivity.ARTIST_ID_KEY)) {
            Log.e(TAG, "Data is not available or does not match the key");
            closeFragmentWithMassage();
            return v;
        }

        int artistId = args.getInt(MainActivity.ARTIST_ID_KEY);
        Artist artist = mDataManager.getArtistById(artistId);
        if (artist == null) {
            Log.e(TAG, "Artist is null");
            closeFragmentWithMassage();
            return v;
        }

        fillActionBar(artist);
        fillView(artist);
        return v;
    }

    private void closeFragmentWithMassage() {
        getActivity().getSupportFragmentManager().popBackStack();
        SystemUtils.toastMassage(getContext(), getContext().getString(R.string.not_artist_data));
    }

    private void fillActionBar(Artist artist) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(artist.getName());
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void fillView(Artist artist) {
        mProgress.startAnimation();
        mProgress.setVisibility(View.VISIBLE);
        PicassoBigCache.INSTANCE.getPicassoBigCache(getContext())
                .load(artist.getCover().getCoverBigUrl())
                .error(R.mipmap.logo)
                .into(mCover, mLoadImageCallback);

        StringBuilder textGenres = new StringBuilder();
        List<Genre> genres = artist.getGenres();
        for (Genre genre : genres) {
            textGenres.append(genre.getGenre()).append(", ");
        }
        if (textGenres.length() != 0) {
            textGenres.deleteCharAt(textGenres.lastIndexOf(","));
        }

        int albumsCount = artist.getAlbumsCount();
        int tracksCount = artist.getTracksCount();

        String textRepertoire = getResources().getQuantityString(
                R.plurals.plurals_albums, albumsCount, albumsCount)
                + ", " + getResources().getQuantityString(
                R.plurals.plurals_tracks, tracksCount, tracksCount);

        String link = artist.getLink();

        mGenres.setText(textGenres);
        mDataRepertoire.setText(textRepertoire);
        mDescription.setText(artist.getDescription());
        if (!TextUtils.isEmpty(link)) {
            mLink.setText(link);
        }
    }

    private void stopProgressBar() {
        mProgress.setVisibility(View.GONE);
        mProgress.stopAnimation();
    }
}
