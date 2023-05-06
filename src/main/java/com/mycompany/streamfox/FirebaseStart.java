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
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

/**
 *
 * @author maazh
 */
public class FirebaseStart {

    public static Firestore db;
    public static FirebaseApp fa;
    public static FirebaseApp rdtb;

    public static void initializeFirebase() throws FileNotFoundException, IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://streamfox-966e7.firebaseio.com/")
                .setFirestoreOptions(FirestoreOptions.getDefaultInstance())
                .setStorageBucket("streamfox-966e7.appspot.com")
                .build();

        fa = FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore(fa);
    }


    public static DatabaseReference getDatabaseReference(String path) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(fa);
        return firebaseDatabase.getReference(path);
    }
}
