/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author maazh
 */
public class TwitchApiEngine {

    public static String accessToken = "";

    public static void initializeTwitch() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .build();

        RequestBody requestBody = new FormBody.Builder()
                .add("client_id", "l8en5dbypf4crtypbx0n8k9vbcxygw")
                .add("client_secret", "bw9p64zme3uq44sf5z9asl2ozsp9n3")
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url("https://id.twitch.tv/oauth2/token")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseData = response.body().string();
            accessToken = responseData.split(":")[1].split(",")[0].replace("\"", "");
        }
    }
}
