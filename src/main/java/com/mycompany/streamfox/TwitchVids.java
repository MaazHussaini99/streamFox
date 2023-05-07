/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.api.client.util.Data;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author maazh
 */
public class TwitchVids {

    private static TwitchApiEngine tae = new TwitchApiEngine();

    public static String gameRequest() throws IOException {
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
            //System.out.println(response.body().string());
            String responseBody = response.body().string();
            return responseBody;
        }
    }

    public static VidObj[][] getTopVideos() throws IOException {
        String id;
        String boxArt;
        VidObj[][] array = new VidObj[5][];
        String games = gameRequest();

        JSONObject json = new JSONObject(games);

        JSONArray dataArray = json.getJSONArray("data");

        for (int j = 0; j < 5; j++) {
            JSONObject dataObj = dataArray.getJSONObject(j);
            System.out.println(dataObj);
            id = dataObj.getString("id");
            boxArt = dataObj.getString("box_art_url");
            OkHttpClient client = new OkHttpClient.Builder()
                    .callTimeout(30, TimeUnit.SECONDS)
                    .build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.twitch.tv/helix/streams").newBuilder();
            urlBuilder.addQueryParameter("game_id", id);
            urlBuilder.addQueryParameter("first", "50");
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Client-ID", "l8en5dbypf4crtypbx0n8k9vbcxygw")
                    .addHeader("Authorization", "Bearer " + tae.accessToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println("Top Videos - Status: " + response.code());
                //System.out.println(response.body().string());

                String responseBody = response.body().string();
                System.out.println(responseBody);
                JSONObject json1 = new JSONObject(responseBody);
                VidObj[] arr = new VidObj[50];
                JSONArray dataArray1 = json1.getJSONArray("data");
                for (int i = 0; i < dataArray1.length(); i++) {

                    JSONObject dataObj1 = dataArray1.getJSONObject(i);

                    String id1 = dataObj1.getString("id");
                    String title = dataObj1.getString("title");
                    String channel = dataObj1.getString("user_name");
                    String thumbnail = dataObj1.getString("thumbnail_url");
//                  System.out.println("Title: " + title);
                    VidObj vid = new VidObj(id1, title, channel, id, thumbnail, boxArt);
                    arr[i] = vid;
                }
                array[j] = arr;
            }
            //System.out.println("***************************");
            //System.out.println(arr.toString());
        }
        System.out.println(array[0][0].title);
        System.out.println(array[2][0].title);
        return array;
    }

    public static void getVideo(String id) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.twitch.tv/helix/videos").newBuilder();

        urlBuilder.addQueryParameter("id", id);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Client-ID", "l8en5dbypf4crtypbx0n8k9vbcxygw")
                .addHeader("Authorization", "Bearer " + tae.accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Top Videos - Status: " + response.code());
            String responseBody = response.body().string();

            JSONObject json = new JSONObject(responseBody);

            JSONArray dataArray = json.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);
                String title = dataObj.getString("title");
                System.out.println(title);
            }
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
