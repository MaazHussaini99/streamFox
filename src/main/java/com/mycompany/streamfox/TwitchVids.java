/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author maazh
 */
public class TwitchVids {

    private static TwitchApiEngine tae = new TwitchApiEngine();

    public static void gameRequest() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url("https://api.twitch.tv/helix/games/top")
                .get()
                .addHeader("Client-ID", "l8en5dbypf4crtypbx0n8k9vbcxygw")
                .addHeader("Authorization", "Bearer " + tae.accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Status: " + response.code());
            System.out.println(response.body().string());
        }
    }

    public static void getTopVideos() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.twitch.tv/helix/videos").newBuilder();

        urlBuilder.addQueryParameter("game_id", "32399");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Client-ID", "l8en5dbypf4crtypbx0n8k9vbcxygw")
                .addHeader("Authorization", "Bearer " + tae.accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Top Videos - Status: " + response.code());
            System.out.println(response.body().string());
        }
    }

    public static void getVideo() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.twitch.tv/helix/videos").newBuilder();

        urlBuilder.addQueryParameter("id", "1802420975");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Client-ID", "l8en5dbypf4crtypbx0n8k9vbcxygw")
                .addHeader("Authorization", "Bearer " + tae.accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Top Videos - Status: " + response.code());
            System.out.println(response.body().string());
        }
    }

    public static void getStreams() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.twitch.tv/helix/streams").newBuilder();

        urlBuilder.addQueryParameter("game_id", "32399");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Client-ID", "l8en5dbypf4crtypbx0n8k9vbcxygw")
                .addHeader("Authorization", "Bearer " + tae.accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Top Videos - Status: " + response.code());
            System.out.println(response.body().string());
        }
    }

}
