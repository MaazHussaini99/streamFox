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

    private Map<String, String> map = new HashMap();
    
    public void setNewProfile(String uid, String email) {
        map.put("fname", "Maaz");
        map.put("lname", "Hussaini");
        map.put("email", email);
        map.put("profileImage", "https://marketplace.canva.com/EAFEits4-uw/1/0/800w/canva-boy-cartoon-gamer-animated-twitch-profile-photo-r0bPCSjUqg0.jpg");
        
        FirebaseStart.db.collection("maaz example").document(uid).collection("settings").document("profile").set(map);
    }

}
