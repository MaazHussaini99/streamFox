/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.streamfox;

/**
 *
 * @author jonat
 */
public class VidObj {

    String id;
    String title;
    String channel;
    String vidLength;
    String gameId;
    String thumbnail;

    public VidObj(String id, String title, String channel) {
        this.id = id;
        this.title = title;
        this.channel = channel;
    }

    public VidObj(String id, String title, String channel, String vidLength) {
        this.id = id;
        this.title = title;
        this.channel = channel;
        this.vidLength = vidLength;
    }

    public VidObj(String id, String title, String channel, String gameId, String thumbnail) {
        this.id = id;
        this.title = title;
        this.channel = channel;
        this.gameId = gameId;
        this.thumbnail = thumbnail;
    }
}
