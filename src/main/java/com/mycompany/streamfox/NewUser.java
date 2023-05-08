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
    private static Map<String, Double> serviceWatchtimeMap = new HashMap();

    public static void setNewUserProfile(String uid, String email, String fname, String lname, String refreshToken) {
        profileMap.put("fname", fname);
        profileMap.put("lname", lname);
        profileMap.put("email", email);
        profileMap.put("refreshToken", refreshToken);
        profileMap.put("profileImage", "https://marketplace.canva.com/EAFEits4-uw/1/0/800w/canva-boy-cartoon-gamer-animated-twitch-profile-photo-r0bPCSjUqg0.jpg");

        FirebaseStart.db.collection("maaz example").document(uid).collection("settings").document("profile").set(profileMap);
    }

    public static void setNewUserServiceList(String uid) {
        serviceListMap.put("hbomax", true);
        serviceListMap.put("hulu", true);
        serviceListMap.put("netflix", true);
        serviceListMap.put("prime", true);
        serviceListMap.put("youtube", true);

        FirebaseStart.db.collection("maaz example").document(uid).collection("settings").document("serviceList").set(serviceListMap);
    }

    public static void setNewUserSettingsWatchtime(String uid) {
        watchtimeMap.put("currentDailyWatchtime", 0);
        watchtimeMap.put("currentWeeklyWatchtime", 0);
        watchtimeMap.put("setDailyLimit", 6);
        watchtimeMap.put("setWeeklyLimit", 50);

        FirebaseStart.db.collection("maaz example").document(uid).collection("settings").document("watchtime").set(watchtimeMap);

    }

    public static void setNewUserServiceWatchtime(String uid) {

        serviceWatchtimeMap.put("currentDailyWatchtime", 0.001);
        serviceWatchtimeMap.put("currentWeeklyWatchtime", 0.001);
        serviceWatchtimeMap.put("setDailyLimit", 0.001);
        serviceWatchtimeMap.put("setWeeklyLimit", 0.001);
        serviceWatchtimeMap.put("sundayWatchTime", 0.001);
        serviceWatchtimeMap.put("mondayWatchTime", 0.001);
        serviceWatchtimeMap.put("tuesdayWatchTime", 0.001);
        serviceWatchtimeMap.put("wednesdayWatchTime", 0.001);
        serviceWatchtimeMap.put("thursdayWatchTime", 0.001);
        serviceWatchtimeMap.put("fridayWatchTime", 0.001);
        serviceWatchtimeMap.put("saturdayWatchTime", 0.001);
        serviceWatchtimeMap.put("WeeklyWatchTime", 0.001);
        FirebaseStart.db.collection("maaz example").document(uid).collection("service").document("youtube").set(serviceWatchtimeMap);

        serviceWatchtimeMap.clear();

        serviceWatchtimeMap.put("currentDailyWatchtime", 0.001);
        serviceWatchtimeMap.put("currentWeeklyWatchtime", 0.001);
        serviceWatchtimeMap.put("setDailyLimit", 0.001);
        serviceWatchtimeMap.put("setWeeklyLimit", 0.001);
        serviceWatchtimeMap.put("sundayWatchTime", 0.001);
        serviceWatchtimeMap.put("mondayWatchTime", 0.001);
        serviceWatchtimeMap.put("tuesdayWatchTime", 0.001);
        serviceWatchtimeMap.put("wednesdayWatchTime", 0.001);
        serviceWatchtimeMap.put("thursdayWatchTime", 0.001);
        serviceWatchtimeMap.put("fridayWatchTime", 0.001);
        serviceWatchtimeMap.put("saturdayWatchTime", 0.001);
        serviceWatchtimeMap.put("WeeklyWatchTime", 0.001);
        FirebaseStart.db.collection("maaz example").document(uid).collection("service").document("twitch").set(serviceWatchtimeMap);
    }
}
