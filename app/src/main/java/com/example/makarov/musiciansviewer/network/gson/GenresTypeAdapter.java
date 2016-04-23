package com.example.makarov.musiciansviewer.network.gson;

import com.example.makarov.musiciansviewer.database.models.Genre;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import io.realm.RealmList;

public class GenresTypeAdapter extends TypeAdapter {

    @Override
    public void write(JsonWriter out, Object value) throws IOException {

    }

    @Override
    public Object read(JsonReader in) throws IOException {
        RealmList<Genre> genres = new RealmList<>();
        in.beginArray();
        while (in.hasNext()) {
            genres.add(new Genre(in.nextString()));
        }
        in.endArray();
        return genres;
    }
}
