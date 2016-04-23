package com.example.makarov.musiciansviewer.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makarov.musiciansviewer.ArtistsApp;
import com.example.makarov.musiciansviewer.DataManager;
import com.example.makarov.musiciansviewer.R;
import com.example.makarov.musiciansviewer.adapters.ArtistsAdapter;
import com.example.makarov.musiciansviewer.database.models.Artist;
import com.example.makarov.musiciansviewer.ui.activity.MainActivity;
import com.example.makarov.musiciansviewer.utils.SystemUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArtistsFragment extends Fragment {

    @Bind(R.id.lv_artists)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private final ArtistsAdapter mArtistsAdapter = new ArtistsAdapter();
    private final DataManager mDataManager = ArtistsApp.getApp().getDataManager();

    private final SwipeRefreshLayout.OnRefreshListener mRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (!SystemUtils.isConnectingToInternet(getContext())) {
                        stopRefreshWithMassage(getContext()
                                .getString(R.string.please_connect_to_internet));
                        return;
                    }
                    mDataManager.getNewArtistsAsync(mOnLoadArtistsCallback);
                }
            };

    private final DataManager.OnLoadArtistsCallback mOnLoadArtistsCallback =
            new DataManager.OnLoadArtistsCallback() {
                @Override
                public void onLoadArtists(List<Artist> list, Exception e) {
                    if (e != null || list == null) {
                        stopRefreshWithMassage(getContext()
                                .getString(R.string.not_data_network));
                        return;
                    }
                    mArtistsAdapter.update(list);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            };

    private final ArtistsAdapter.OnClickArtistListener mClickArtistListener =
            new ArtistsAdapter.OnClickArtistListener() {
                @Override
                public void onClick(int artistId) {
                    ((MainActivity) getActivity()).openArtistDetailFragment(artistId);
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.artists_fragment, null);
        ButterKnife.bind(this, v);
        fillActionBar();

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mArtistsAdapter.setOnClickArtistListener(mClickArtistListener);
        mRecyclerView.setAdapter(mArtistsAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener);

        mDataManager.getArtistsAsync(mOnLoadArtistsCallback);
        return v;
    }

    private void stopRefreshWithMassage(String massage) {
        SystemUtils.toastMassage(getContext(), massage);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void fillActionBar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.artists);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
}
