package com.csc229labfiles.googlefirestoretest;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class StreamFoxFB {


    public void addData(String collectionName, String docName,String name,String age, String location) throws IOException, ExecutionException, InterruptedException {
        FileInputStream serviceAccount =
                new FileInputStream("./serviceAccountKey.json");
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId("streamfox-966e7")
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
        Firestore db = firestoreOptions.getService();
        Map<String, Object> docData = new HashMap<>();
        docData.put("name", name);
        docData.put("age",age);
        docData.put("location", location);
// Add a new document (asynchronously) in collection "cities" with id "LA"
        ApiFuture<WriteResult> future = db.collection(collectionName).document(docName).set(docData);
// ...
// future.get() blocks on response
        System.out.println("Update time : " + future.get().getUpdateTime());
    }

    public void getData() throws IOException, ExecutionException, InterruptedException {
        FileInputStream serviceAccount =
                new FileInputStream("./serviceAccountKey.json");
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId("streamfox-966e7")
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
        Firestore db = firestoreOptions.getService();

        ApiFuture<QuerySnapshot> future = db.collection("PersonExperiment").get();
// future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println(document.getId() + " => " + document.toObject(Person.class).toString());
        }
    }

    public void deleteData(String collectionName,String docName) throws IOException, ExecutionException, InterruptedException {
        FileInputStream serviceAccount =
                new FileInputStream("./serviceAccountKey.json");
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId("streamfox-966e7")
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
        // asynchronously delete a document
        Firestore db = firestoreOptions.getService();
        ApiFuture<WriteResult> writeResult = db.collection(collectionName).document(docName).delete();
// ...
        System.out.println("Update time : " + writeResult.get().getUpdateTime());
    }

    public void updateData(String colName,String docName,String updateField,String data) throws IOException, ExecutionException, InterruptedException {
        //Update an existing document
        FileInputStream serviceAccount =
                new FileInputStream("./serviceAccountKey.json");
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId("streamfox-966e7")
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
        Firestore db = firestoreOptions.getService();
        DocumentReference docRef = db.collection(colName).document(docName);

// (async) Update one field
        ApiFuture<WriteResult> future = docRef.update(updateField,data);

// ...
        WriteResult result = future.get();
        System.out.println("Write result: " + result);
    }




}
