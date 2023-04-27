/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.core.ApiFuture;
import com.google.api.services.youtube.YouTube;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import com.sun.net.httpserver.*;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author maazh
 */
public class YoutubeApiEngine {

    private static final String CS = "src/main/resources/youtube.json";
    static YouTube youtubeService;
    private static final Collection<String> SCOPES
            = Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl");

    private static final String APPLICATION_NAME = "myCmsMaaz";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static String refreshToken = "";
    static User user = User.getInstance();

    public static void initializeYoutube() {

//        try {
//            youtubeService = getService();
//        } catch (GeneralSecurityException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        Map<String, Object> profileDataMap = null;
        DocumentReference docRef = FirebaseStart.db.collection("maaz example").document(user.getUid()).collection("settings").document("profile");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            profileDataMap = future.get().getData();
            refreshToken = (String) profileDataMap.get("refreshToken");
            if (refreshToken == "") {
                try {
                    youtubeService = getService();
                    profileDataMap.put("refreshToken", refreshToken);
                    ApiFuture<WriteResult> update = FirebaseStart.db.collection("maaz example").document(user.getUid()).collection("settings").document("profile").update(profileDataMap);
                } catch (GeneralSecurityException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    youtubeService = getServiceWithRefreshToken(refreshToken);
                } catch (GeneralSecurityException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Create an authorized Credential object.
     *
     * @param httpTransport
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream("src/main/resources/youtube.json");
        System.out.println("InputStream: " + in.toString()); // Debug statement
        String json = new Scanner(in).useDelimiter("\\Z").next();
        System.out.println("JSON: " + json); // Debug statement
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new StringReader(json));
        // Build flow and trigger user authorization request1.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES).setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        refreshToken = credential.getRefreshToken();
        System.out.println("Access token: " + credential.getRefreshToken());
        return credential;
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static YouTube getServiceWithRefreshToken(String refreshToken) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        // Load client secrets.
        InputStream in = new FileInputStream(CS);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build a new token request using the refresh token
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);

        // Build a new credential using the client secrets and the token response
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(clientSecrets)
                .build()
                .setFromTokenResponse(tokenResponse);

        // Build and return an authorized API client service using the credential
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
