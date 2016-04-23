package com.example.makarov.musiciansviewer.database.models;

import io.realm.RealmObject;

public class Genre extends RealmObject {

    private String genre;

    public Genre() {

    }

    public Genre(String genre) {
        this.genre = genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
