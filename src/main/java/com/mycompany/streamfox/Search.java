/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author maazh
 */
public class Search {

    public static SearchListResponse getSearchResults(String keyword) throws IOException {

        YouTube.Search.List request = YoutubeApiEngine.youtubeService.search()
                .list("snippet");
        SearchListResponse response = request.setMaxResults(25L)
                .setQ(keyword)
                .execute();

//        System.out.println("Comment: " + response.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getTextOriginal());
//        System.out.println("Name:" + response.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
//        System.out.println("Image URL:" + response.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl());
//        System.out.println("Search: " + response);
        return response;
    }

    public static VidObj[] returnArray(String keyword) {
        VidObj[] help = new VidObj[25];
        try {
            SearchListResponse searchResult = getSearchResults(keyword);
            for (int i = 0; i < 25; i++) {
                help[i] = new VidObj(
                        searchResult.getItems().get(i).getId().getVideoId(),
                        searchResult.getItems().get(i).getSnippet().getTitle(),
                        searchResult.getItems().get(i).getSnippet().getChannelTitle());
            }
            //System.out.println(help[0].title);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return help;
    }
    
      
     
    
}