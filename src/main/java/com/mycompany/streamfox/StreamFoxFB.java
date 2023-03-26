package com.mycompany.streamfox;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StreamFoxFB {

    public void addInitData(String collectionName) throws IOException, ExecutionException, InterruptedException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("streamfox-966e7").setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        Firestore db = firestoreOptions.getService();
        //get current UID
        User user = User.getInstance();
        //user.setUserEmail(email);
        //user.setUid(userRecord.getUid());

        AccountInfo AccountData = new AccountInfo("Eren Jaeafer", "sadjkdsajkkdjsall", user.getUserEmail(), "9/26/1996", true, false);
        DurationSettings DurationData = new DurationSettings("10hours", "5hours", "32", "100hr");
        Netflix uNetflix = new Netflix("ohioman", "bobbyflay44", "Netflix", "10 hours", "100 hours", "Ohioman", "sjkjksdklsdakljdskjl");
        Disney uDisney = new Disney("ohioman", "bobbyflay44", "Netflix", "10 hours", "100 hours", "Ohioman", "sjkjksdklsdakljdskjl");
        Youtube youtube = new Youtube("ohioman", "bobbyflay44", "Netflix", "10 hours", "100 hours", "Ohioman", "sjkjksdklsdakljdskjl");

        UserDocument userData = new UserDocument(Arrays.asList(AccountData, DurationData), Arrays.asList(uNetflix, uDisney, youtube));
        ApiFuture<WriteResult> future = db.collection("Unique registered User").document(user.getUid()).create(userData);

// future.get() blocks on response
        //System.out.println("Added document with ID: " + future.get().getId());
    }

    public void getAllData() throws IOException, ExecutionException, InterruptedException {
        User user = User.getInstance();
         FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("streamfox-966e7").setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        Firestore db = firestoreOptions.getService();

        DocumentReference docRef = db.collection("Unique registered User").document(user.getUid());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        System.out.println(future.get().getData());
    }

    public void deleteData(String collectionName, String docName) throws IOException, ExecutionException, InterruptedException {
        User user = User.getInstance();
         FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("streamfox-966e7").setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        Firestore db = firestoreOptions.getService();
        // asynchronously delete a document

        ApiFuture<WriteResult> writeResult = db.collection(collectionName).document(docName).delete();
// ...
        System.out.println("Update time : " + writeResult.get().getUpdateTime());
    }

//    public void updateData(String colName, String docName, String updateField, String data) throws IOException, ExecutionException, InterruptedException {
//        User user = User.getInstance();
//         FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
//        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId("streamfox-966e7").setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
//        Firestore db = firestoreOptions.getService();
//        
//        DocumentReference docRef = db.collection(colName).document(docName);
//
//// (async) Update one field
//        ApiFuture<WriteResult> future = docRef.update(updateField, data);
//
//// ...
//        WriteResult result = future.get();
//        System.out.println("Write result: " + result);
//
//    }

}
