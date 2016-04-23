package com.example.makarov.musiciansviewer.network.robospice.model;

import com.example.makarov.musiciansviewer.database.models.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistsList {

    private final List<Artist> mArtists;

    public ArtistsList() {
        mArtists = new ArrayList<>();
    }

    public List<Artist> getArtists() {
        return mArtists;
    }
}
