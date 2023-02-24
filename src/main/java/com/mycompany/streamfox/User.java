/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.firebase.auth.UserRecord;

/**
 *
 * @author maazh
 */
public final class User {
    
    private final static User INSTANCE = new User();
    private String userEmail;
    private String uid;

    private User() {
    }
    
    public static User getInstance(){
        return INSTANCE;
    }
    
    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public String toString() {
        return "User{" + "userEmail=" + userEmail + ", uid=" + uid + '}';
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
