/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

/**
 *
 * 
 */
public class Netflix {
  
 
 private String Username;
 private String Password; 
 private String StreamingServiceName ; 
 private String DailyWatchTime ; 
 private String WeeklyWatchTime;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    private String videoName;

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    private String videoID;


    public Netflix(String Username, String Password, String StreamingServiceName, String DailyWatchTime, String WeeklyWatchTime, String videoName, String videoID) {
        this.Username = Username;
        this.Password = Password;
        this.StreamingServiceName = StreamingServiceName;
        this.DailyWatchTime = DailyWatchTime;
        this.WeeklyWatchTime = WeeklyWatchTime; 
        this.videoName=videoName;
        this.videoID=videoID;

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getStreamingServiceName() {
        return StreamingServiceName;
    }

    public void setStreamingServiceName(String StreamingServiceName) {
        this.StreamingServiceName = StreamingServiceName;
    }

    public String getDailyWatchTime() {
        return DailyWatchTime;
    }

    public void setDailyWatchTime(String DailyWatchTime) {
        this.DailyWatchTime = DailyWatchTime;
    }

    public String getWeeklyWatchTime() {
        return WeeklyWatchTime;
    }

    public void setWeeklyWatchTime(String WeeklyWatchTime) {
        this.WeeklyWatchTime = WeeklyWatchTime;
    }
 
    
}
    
    
    

