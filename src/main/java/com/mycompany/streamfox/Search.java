/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import java.io.IOException;

/**
 *
 * @author maazh
 */
public class Search {

    public static SearchListResponse getSearchResults() throws IOException {

        YouTube.Search.List request = YoutubeApiEngine.youtubeService.search()
                .list("snippet");
        SearchListResponse response = request.setMaxResults(25L)
                .setQ("surfing")
                .execute();

//        System.out.println("Comment: " + response.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getTextOriginal());
//        System.out.println("Name:" + response.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
//        System.out.println("Image URL:" + response.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl());
        System.out.println(response);
        return response;
    }
}
