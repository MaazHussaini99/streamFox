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

    private final static UserData INSTANCE = new UserData();
    private Map<String, Object> profileDataMap;
    private Map<String, Object> serviceListDataMap;
    private Map<String, Object> watchTimeDataMap;

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

    UserData() {
        setProfile();
        setServiceList();
        setWatchtime();
    }

    public void setProfile() {
        DocumentReference docRef = FirebaseStart.db.collection("maaz example").document(user.getUid()).collection("settings").document("profile");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            profileDataMap = future.get().getData();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    public void updateProfile(Map<String, Object> profileMap) {
        FirebaseStart.db.collection("maaz example").document(user.getUid()).collection("settings").document("profile").set(profileMap);
    }

    
    private void setServiceList() {
        DocumentReference docRef = FirebaseStart.db.collection("maaz example").document(user.getUid()).collection("settings").document("servicesList");
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
        DocumentReference docRef = FirebaseStart.db.collection("maaz example").document(user.getUid()).collection("settings").document("watchtime");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            watchTimeDataMap = future.get().getData();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    public static UserData getInstance() {
        return INSTANCE;
    }

}
