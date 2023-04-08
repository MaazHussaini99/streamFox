/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author maazh
 */
public class NewUser {

    private static Map<String, String> profileMap = new HashMap();
    private static Map<String, Boolean> serviceListMap = new HashMap();
    private static Map<String, Integer> watchtimeMap = new HashMap();
    

    public static void setNewProfile(String uid, String email, String fname, String lname, String refreshToken) {
        profileMap.put("fname", fname);
        profileMap.put("lname", lname);
        profileMap.put("email", email);
        profileMap.put("refreshToken", refreshToken);
        profileMap.put("profileImage", "https://marketplace.canva.com/EAFEits4-uw/1/0/800w/canva-boy-cartoon-gamer-animated-twitch-profile-photo-r0bPCSjUqg0.jpg");

        FirebaseStart.db.collection("maaz example").document(uid).collection("settings").document("profile").set(profileMap);
    }

    public static void setNewServiceList(String uid) {
        serviceListMap.put("hbomax", true);
        serviceListMap.put("hulu", true);
        serviceListMap.put("netflix", true);
        serviceListMap.put("prime", true);
        serviceListMap.put("youtube", true);

        FirebaseStart.db.collection("maaz example").document(uid).collection("settings").document("serviceList").set(serviceListMap);
    }

    public static void setNewWatchtime(String uid) {
        watchtimeMap.put("currentDailyWatchtime", 0);
        watchtimeMap.put("currentWeeklyWatchtime", 0);
        watchtimeMap.put("setDailyLimit", 6);
        watchtimeMap.put("setWeeklyLimit", 50);

        FirebaseStart.db.collection("maaz example").document(uid).collection("settings").document("watchtime").set(watchtimeMap);
    }

    public static void setServiceWatchtime(String uid) {

        watchtimeMap.clear();
        watchtimeMap.put("currentDailyWatchtime", 0);
        watchtimeMap.put("currentWeeklyWatchtime", 0);
        watchtimeMap.put("setDailyLimit", 6);
        watchtimeMap.put("setWeeklyLimit", 50);
        FirebaseStart.db.collection("maaz example").document(uid).collection("service").document("netflix").set(watchtimeMap);

        watchtimeMap.clear();

        watchtimeMap.put("currentDailyWatchtime", 0);
        watchtimeMap.put("currentWeeklyWatchtime", 0);
        watchtimeMap.put("setDailyLimit", 6);
        watchtimeMap.put("setWeeklyLimit", 50);

        FirebaseStart.db.collection("maaz example").document(uid).collection("service").document("youtube").set(watchtimeMap);

        watchtimeMap.clear();

        watchtimeMap.put("currentDailyWatchtime", 0);
        watchtimeMap.put("currentWeeklyWatchtime", 0);
        watchtimeMap.put("setDailyLimit", 6);
        watchtimeMap.put("setWeeklyLimit", 50);

        FirebaseStart.db.collection("maaz example").document(uid).collection("service").document("hbomax").set(watchtimeMap);

        watchtimeMap.clear();

        watchtimeMap.put("currentDailyWatchtime", 0);
        watchtimeMap.put("currentWeeklyWatchtime", 0);
        watchtimeMap.put("setDailyLimit", 6);
        watchtimeMap.put("setWeeklyLimit", 50);

        FirebaseStart.db.collection("maaz example").document(uid).collection("service").document("hulu").set(watchtimeMap);

        watchtimeMap.clear();

        watchtimeMap.put("currentDailyWatchtime", 0);
        watchtimeMap.put("currentWeeklyWatchtime", 0);
        watchtimeMap.put("setDailyLimit", 6);
        watchtimeMap.put("setWeeklyLimit", 50);

        FirebaseStart.db.collection("maaz example").document(uid).collection("service").document("prime").set(watchtimeMap);
    }
}
