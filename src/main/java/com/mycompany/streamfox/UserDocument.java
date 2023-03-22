/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;


import java.util.List;

/**
 *

 */
public class UserDocument {




    private AccountInfo uAccountInfo;

    public AccountInfo getuAccountInfo() {
        return uAccountInfo;
    }

    public void setuAccountInfo(AccountInfo uAccountInfo) {
        this.uAccountInfo = uAccountInfo;
    }




    private DurationSettings uDurationSettings;
    public DurationSettings getuDurationSettings() {
        return uDurationSettings;
    }

    public void setuDurationSettings(DurationSettings uDurationSettings) {
        this.uDurationSettings = uDurationSettings;
    }


    private List<Object> Settings;
    public List<Object> getSettings() {
        return Settings;
    }

    public void setSettings(List<Object> settings) {
        Settings = settings;
    }

    private Netflix uNetflix;
    private Disney uDisney;
    private Youtube uYoutube;



    public Netflix getuNetflix() {
        return uNetflix;
    }

    public void setuNetflix(Netflix uNetflix) {
        this.uNetflix = uNetflix;
    }

    public Disney getuDisney() {
        return uDisney;
    }

    public void setuDisney(Disney uDisney) {
        this.uDisney = uDisney;
    }

    public Youtube getuYoutube() {
        return uYoutube;
    }

    public void setuYoutube(Youtube uYoutube) {
        this.uYoutube = uYoutube;
    }

    private List<Object> StreamingService;
    public List<Object> getStreamingService() {
        return StreamingService;
    }

    public void setStreamingService(List<Object> streamingService) {
        StreamingService = streamingService;
    }






    public UserDocument(List<Object> settings,List<Object> StreamingService) {
        this.Settings = settings;
        this.StreamingService=StreamingService;

    }
}
