/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.streamfox;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import static com.mycompany.streamfox.YoutubeApiEngine.youtubeService;
import java.io.IOException;

/**
 *
 * @author maazh
 */
public class YoutubeVids {

    public static VideoListResponse getMostPopularVids() throws IOException {

        YouTube.Videos.List request1 = youtubeService.videos()
                .list("snippet,contentDetails,statistics");
        VideoListResponse response = request1.setMaxResults(50L).setChart("mostPopular")
                .setRegionCode("US")
                .execute();

        System.out.println(response);
        return response;
    }

    public static SearchListResponse getRelatedVids(String id) throws IOException {

        YouTube.Search.List request = youtubeService.search()
            .list("snippet");
        SearchListResponse response = request.setMaxResults(21L).setRelatedToVideoId(id)
            .setType("video")
            .execute();

        System.out.println(response);
        return response;
    }
}
