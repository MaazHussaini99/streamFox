package databasepackage;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StreamFoxFB {



    public void addData(String collectionName) throws IOException, ExecutionException, InterruptedException {
        FileInputStream serviceAccount =
                new FileInputStream("./serviceAccountKey.json");
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId("streamfox-966e7")
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
        Firestore db = firestoreOptions.getService();
        AccountInfo AccountData=new AccountInfo("Eren Jaeafer","sadjkdsajkkdjsall","AttackTitan69","9/26/1996",true,false);
        DurationSettings DurationData=new DurationSettings("10hours","5hours","32","100hr");
        Netflix uNetflix=new Netflix("ohioman","bobbyflay44","Netflix","10 hours","100 hours","Ohioman","sjkjksdklsdakljdskjl");
       Disney uDisney=new Disney("ohioman","bobbyflay44","Netflix","10 hours","100 hours","Ohioman","sjkjksdklsdakljdskjl");
        Youtube youtube=new Youtube("ohioman","bobbyflay44","Netflix","10 hours","100 hours","Ohioman","sjkjksdklsdakljdskjl");
       UserDocument Userdata=new UserDocument(Arrays.asList(AccountData,DurationData),Arrays.asList(uNetflix,uDisney,youtube));
        //Map<String, Object> docData = new HashMap<>();
        //docData.put("name", name);
        //docData.put("age",age);
        //docData.put("location", location);
// Add a new document (asynchronously) in collection "cities" with id "LA"
        ApiFuture<DocumentReference> future = db.collection("Unique registered User").add(Userdata);
// future.get() blocks on response
        System.out.println("Added document with ID: " + future.get().getId());
    }
    
/*
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
*/
}
    





