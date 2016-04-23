package com.example.makarov.musiciansviewer.adapters;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makarov.musiciansviewer.R;
import com.example.makarov.musiciansviewer.database.PicassoBigCache;
import com.example.makarov.musiciansviewer.database.models.Artist;
import com.example.makarov.musiciansviewer.database.models.Genre;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistHolder> {

    private final List<Artist> mArtists = new ArrayList<>();
    @Nullable private OnClickArtistListener mClickArtistListener;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Artist artist = (Artist) v.getTag();
            if (mClickArtistListener != null) {
                mClickArtistListener.onClick(artist.getId());
            }
        }
    };

    public interface OnClickArtistListener {
        void onClick(int artistId);
    }

    public ArtistsAdapter() {
    }

    public ArtistsAdapter(List<Artist> artists) {
        mArtists.addAll(artists);
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artist, parent, false);

        return new ArtistHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistHolder holder, final int position) {
        holder.itemView.setOnClickListener(mOnClickListener);
        holder.fillView(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    private Artist getItem(int position) {
        return mArtists.get(position);
    }

    public void setOnClickArtistListener(OnClickArtistListener listener) {
        mClickArtistListener = listener;
    }

    public void update(List<Artist> artists) {
        mArtists.clear();
        mArtists.addAll(artists);
        notifyDataSetChanged();
    }

    public static class ArtistHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView mCover;
        @Bind(R.id.name)
        TextView mName;
        @Bind(R.id.genres)
        TextView mGenres;
        @Bind(R.id.repertoire_data)
        TextView mRepertoireData;
        @Bind(R.id.progress_load)
        CircularProgressView mProgress;

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

        public ArtistHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fillView(Artist artist) {
            itemView.setTag(artist);

            mProgress.startAnimation();
            mProgress.setVisibility(View.VISIBLE);
            PicassoBigCache.INSTANCE.getPicassoBigCache(itemView.getContext())
                    .load(artist.getCover().getCoverSmallUrl())
                    .error(R.mipmap.load_failed)
                    .into(mCover, mLoadImageCallback);

            String textName = artist.getName();

            StringBuilder textGenres = new StringBuilder();
            List<Genre> genres = artist.getGenres();
            for (Genre genre : genres) {
                textGenres.append(genre.getGenre()).append(", ");
            }
            if (textGenres.length() != 0) {
                textGenres.deleteCharAt(textGenres.lastIndexOf(","));
            }

            Resources resources = itemView.getResources();
            int albumsCount = artist.getAlbumsCount();
            int tracksCount = artist.getTracksCount();

            String textRepertoire = resources.getQuantityString(
                    R.plurals.plurals_albums, albumsCount, albumsCount)
                    + ", " + resources.getQuantityString(
                    R.plurals.plurals_tracks, tracksCount, tracksCount);

            mName.setText(textName);
            mGenres.setText(textGenres);
            mRepertoireData.setText(textRepertoire);
        }

        private void stopProgressBar() {
            mProgress.setVisibility(View.GONE);
            mProgress.stopAnimation();
        }
    }
}
