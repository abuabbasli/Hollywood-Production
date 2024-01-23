package com.example.hollywoodProduction.Services;


import com.example.hollywoodProduction.Entity.Video;

import java.util.List;

import com.example.hollywoodProduction.Response.ImpressionsViewsResponse;
import org.springframework.stereotype.Service;

public interface VideoService {

     Video saveVideo(Video video);
    Video getVideoById(Long id);
    List<Video> getAllVideos();
    Video updateVideo(Video video, Long id);
    void deleteVideo(Long id);

    Video publishVideo(Video video);

    Video editVideo(Long id, Video video);

    void delistVideo(Long id);

    Video loadVideo(Long userId, Long videoId);

    String playVideo(Long userId, Long videoId);

    List<Video> listAllVideos();

   ImpressionsViewsResponse getTotalImpressionsAndViews(Long videoId);

    List<Video> searchByAttribute(String attributeName, String searchValue);

}

