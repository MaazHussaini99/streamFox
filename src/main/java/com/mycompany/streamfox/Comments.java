/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import java.io.IOException;

/**
 *
 * @author maazh
 */
public class Comments {

    private static CommentThreadListResponse response;

    public static CommentThreadListResponse getCommentsFromVideo(String videoId) throws IOException {

        YouTube.CommentThreads.List request = YoutubeApiEngine.youtubeService.commentThreads()
                .list("snippet");
        response = request.setKey("src/main/resources/youtube.json")
                .setVideoId(videoId)
                .execute();
        
        
        System.out.println("Comment: " + response.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getTextOriginal());
        System.out.println("Name:" + response.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
        System.out.println("Image URL:" + response.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl());
        return response;
    }
}