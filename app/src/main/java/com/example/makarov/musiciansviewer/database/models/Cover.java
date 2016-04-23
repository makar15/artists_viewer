package com.example.makarov.musiciansviewer.database.models;

import io.realm.RealmObject;

public class Cover extends RealmObject {

    public static final String COVER_SMALL = "small";
    public static final String COVER_BIG = "big";

    private String coverSmallUrl;
    private String coverBigUrl;

    public Cover() {

    }

    public void setCoverSmallUrl(String coverSmallUrl) {
        this.coverSmallUrl = coverSmallUrl;
    }

    public void setCoverBigUrl(String coverBigUrl) {
        this.coverBigUrl = coverBigUrl;
    }

    public String getCoverBigUrl() {
        return coverBigUrl;
    }

    public String getCoverSmallUrl() {
        return coverSmallUrl;
    }
}
