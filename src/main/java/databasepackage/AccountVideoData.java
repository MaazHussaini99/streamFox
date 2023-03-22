/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package databasepackage;

import java.util.Objects;

/**
 *
 *
 */
public class AccountVideoData {
private String VideoId; 
private String VideoName; 
private Boolean Favorites; 
private Boolean Recently_watched; 
private Boolean Resume ;  


 public AccountVideoData(String VideoId, String VideoName) {
        this.VideoId = VideoId;
        this.VideoName = VideoName;
        this.Resume = false; 
        this.Recently_watched = false; 
        this.Favorites = false ; 
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
  @Override
    public String toString() {
        return "AccountVideoData{" + "VideoId=" + VideoId + ", VideoName=" + VideoName + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.VideoId);
        hash = 67 * hash + Objects.hashCode(this.VideoName);
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
        final AccountVideoData other = (AccountVideoData) obj;
        if (!Objects.equals(this.VideoId, other.VideoId)) {
            return false;
        }
        return Objects.equals(this.VideoName, other.VideoName);
    }

   
    
}
