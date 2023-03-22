/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package databasepackage;

import java.util.Objects;



public class YoutubeVideoData {

private String VideoId; 
private String VideoName; 
private Boolean Likes;
private Boolean Dislikes;
private Boolean Subscribe; 
private Boolean Recently_watched; 
private Boolean Resume ;  
private String URL ;  


    public YoutubeVideoData(String VideoId, String VideoName, String URL) {
        this.VideoId = VideoId;
        this.VideoName = VideoName;
        this.URL = URL;
        this.Likes = false ; 
        this.Dislikes = false ; 
        this.Subscribe = false ;
        this.Recently_watched = false ;
        this.Resume = false ; 
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String VideoId) {
        this.VideoId = VideoId;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String VideoName) {
        this.VideoName = VideoName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.VideoId);
        hash = 97 * hash + Objects.hashCode(this.VideoName);
        hash = 97 * hash + Objects.hashCode(this.URL);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final YoutubeVideoData other = (YoutubeVideoData) obj;
        if (!Objects.equals(this.VideoId, other.VideoId)) {
            return false;
        }
        if (!Objects.equals(this.VideoName, other.VideoName)) {
            return false;
        }
        return Objects.equals(this.URL, other.URL);
    }

    @Override
    public String toString() {
        return "YoutubeVideoData{" + "VideoId=" + VideoId + ", VideoName=" + VideoName + ", URL=" + URL + '}';
    }



  }
