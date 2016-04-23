package com.example.makarov.musiciansviewer.network.okhttp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class API {

    private static final String HTTP_HEADER = "http://";
    private static final OkHttpClient mClient = new OkHttpClient();

    public static String getResponse(String url) throws IOException {
        if (!url.contains(HTTP_HEADER)) {
            url = HTTP_HEADER + url;
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }

}
