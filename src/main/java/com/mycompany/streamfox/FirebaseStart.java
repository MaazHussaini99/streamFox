/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import java.io.IOException;

/**
 *
 * @author maazh
 */
public class FirebaseStart {

    public static Firestore db;
    public static FirebaseApp fa;
    public static void initializeFirebase() throws FileNotFoundException, IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://streamfox-966e7.firebaseio.com/")
                .setStorageBucket("streamfox-966e7.appspot.com")
                .build();

        fa = FirebaseApp.initializeApp(options);
    }

    @SuppressWarnings("ThrowableResultIgnored")
    public static void initializeFirestore() {
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        FirestoreOptions firestoreOptions = null;
        try {
            firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("streamfox-966e7").setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
            db = firestoreOptions.getService();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
