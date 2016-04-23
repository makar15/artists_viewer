package com.example.makarov.musiciansviewer.database.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Artist extends RealmObject {

    public static final String ID = "id";

    private String name;
    private String link;
    private RealmList<Genre> genres;
    private String description;
    private Cover cover;
    @SerializedName("tracks")
    private int tracksCount;
    @SerializedName("albums")
    private int albumsCount;
    @PrimaryKey private int id;

    public Artist() {

    }

    public Artist(int id, String name, String link, String description, RealmList<Genre> genres,
                  Cover cover, int tracksCount, int albumsCount) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.description = description;
        this.genres = genres;
        this.cover = cover;
        this.tracksCount = tracksCount;
        this.albumsCount = albumsCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setGenres(RealmList<Genre> genres) {
        this.genres = genres;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public void setTracksCount(int tracksCount) {
        this.tracksCount = tracksCount;
    }

    public void setAlbumsCount(int albumsCount) {
        this.albumsCount = albumsCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public RealmList<Genre> getGenres() {
        return genres;
    }

    public String getDescription() {
        return description;
    }

    public Cover getCover() {
        return cover;
    }

    public int getTracksCount() {
        return tracksCount;
    }

    public int getAlbumsCount() {
        return albumsCount;
    }

    public int getId() {
        return id;
    }
}
