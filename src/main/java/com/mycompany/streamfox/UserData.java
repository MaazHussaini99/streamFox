/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author maazh
 */
public final class UserData {

    @Override
    public String toString() {
        return "UserData{" + "profileDataMap=" + profileDataMap + ", serviceListDataMap=" + serviceListDataMap + ", watchTimeDataMap=" + watchTimeDataMap + ", videoTrackingDataMap=" + videoTrackingDataMap + ", NetflixDataMap=" + NetflixDataMap + ", YoutubeDataMap=" + YoutubeDataMap + ", user=" + user + '}';
    }

    

    private final static UserData INSTANCE = new UserData();
    private Map<String, Object> profileDataMap;
    private Map<String, Object> serviceListDataMap;
    private Map<String, Object> watchTimeDataMap;
     private Map<String, Object> videoTrackingDataMap;
    private Map<String, Object> NetflixDataMap;
    private Map<String, Object> YoutubeDataMap;


    public Map<String, Object> getProfileDataMap() {
        return profileDataMap;
    }

    public Map<String, Object> getServiceListDataMap() {
        return serviceListDataMap;
    }

    public Map<String, Object> getWatchTimeDataMap() {
        return watchTimeDataMap;
    }
    User user = User.getInstance();

    
     public Map<String, Object> getNetfilxDataMap() {
        return  NetflixDataMap;
    }
     
    public Map<String, Object> getYoutubeDataMap() {
        return  YoutubeDataMap;
    }
    public Map<String, Object> getvideoTrackingDataMap() {
        return videoTrackingDataMap;
    }

    //login
    //create new map for service collection
    //create new map for video tracking
    
    //then create function that adds data into that map from firebase
    UserData() {
        setProfile();
        setServiceList();
        setWatchtime();
        setVideoTracking();
         setNetflixData();
         setYoutubeData();
    }

    private void setProfile() {
        DocumentReference docRef = FirebaseStart.db.collection("maaz example").document("user").collection("settings").document("profile");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            profileDataMap = future.get().getData();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    private void setServiceList() {
        DocumentReference docRef = FirebaseStart.db.collection("maaz example").document("user").collection("settings").document("servicesList");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            serviceListDataMap = future.get().getData();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    private void setWatchtime() {
        DocumentReference docRef = FirebaseStart.db.collection("maaz example").document("user").collection("settings").document("watchtime");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            watchTimeDataMap = future.get().getData();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }
    
    private void setVideoTracking(){
           DocumentReference docRef = FirebaseStart.db.collection("maaz example").document("user").collection("tracking").document("videoTracking");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            videoTrackingDataMap = future.get().getData();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    public static UserData getInstance() {
        return INSTANCE;
    }
    
        private void setNetflixData(){
           DocumentReference docRef = FirebaseStart.db.collection("maaz example").document("user").collection("service").document("netflix");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            NetflixDataMap = future.get().getData();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }
        
         private void setYoutubeData(){
           DocumentReference docRef = FirebaseStart.db.collection("maaz example").document("user").collection("service").document("youtube");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            YoutubeDataMap = future.get().getData();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }

}
