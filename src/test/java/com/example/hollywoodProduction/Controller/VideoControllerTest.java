package com.example.hollywoodProduction.Controller;
import com.example.hollywoodProduction.Response.ImpressionsViewsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.hollywoodProduction.Entity.Video;
import com.example.hollywoodProduction.Services.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(VideoController.class)
public class VideoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VideoService videoService;

    private Video sampleVideo;

    @BeforeEach
    public void setUp() {
        sampleVideo = new Video();
        sampleVideo.setId(1L);
        sampleVideo.setTitle("Sample Video");
    }

    @Test
    public void testPublishVideo() throws Exception {
        when(videoService.publishVideo(any(Video.class))).thenReturn(sampleVideo);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleVideo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(sampleVideo.getTitle()));
    }

    @Test
    public void testEditVideo() throws Exception {
        Long videoId = 1L;
        when(videoService.editVideo(eq(videoId), any(Video.class))).thenReturn(sampleVideo);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/videos/{id}/edit", videoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleVideo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(sampleVideo.getTitle()));
    }



    @Test
    public void testDelistVideo() throws Exception {
        Long videoId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/videos/video/{id}", videoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetVideoById() throws Exception {
        Long videoId = 1L;
        when(videoService.getVideoById(videoId)).thenReturn(sampleVideo);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/videos/{id}", videoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(sampleVideo.getTitle()));
    }

    @Test
    public void testListAllVideos() throws Exception {
        List<Video> videos = Collections.singletonList(sampleVideo);
        when(videoService.listAllVideos()).thenReturn(videos);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/videos/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(sampleVideo.getTitle()));
    }

    @Test
    public void testSearchVideos() throws Exception {
        String attribute = "title";
        String value = "Sample";
        List<Video> videos = Collections.singletonList(sampleVideo);
        when(videoService.searchByAttribute(attribute, value)).thenReturn(videos);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/videos/search")
                        .param("attribute", attribute)
                        .param("value", value)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(sampleVideo.getTitle()));
    }

    @Test
    public void testDeleteVideo() throws Exception {
        Long videoId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/videos/{id}", videoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoadVideo() throws Exception {
        Long userId = 1L;
        Long videoId = 2L;
        when(videoService.loadVideo(userId, videoId)).thenReturn(sampleVideo);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/videos/{userId}/load/{videoId}", userId, videoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(sampleVideo.getTitle()));
    }

    @Test
    public void testPlayVideo() throws Exception {
        Long userId = 1L;
        Long videoId = 2L;
        String s3ObjectKey = "video/123.mp4";
        when(videoService.playVideo(userId, videoId)).thenReturn(s3ObjectKey);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/videos/{userId}/play/{videoId}", userId, videoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(s3ObjectKey));
    }

    @Test
    public void testGetTotalImpressionsAndViews() {
        // Create a mock instance of VideoService
        VideoService videoService = Mockito.mock(VideoService.class);

        // Create an instance of ImpressionsViewsResponse for the expected response
        ImpressionsViewsResponse expectedResponse = new ImpressionsViewsResponse(10L, 20L);

        // Configure the mock to return the expected response when getTotalImpressionsAndViews is called
        when(videoService.getTotalImpressionsAndViews(anyLong())).thenReturn(expectedResponse);

        // Create an instance of the VideoController and inject the mock VideoService
        VideoController videoController = new VideoController(videoService);

        // Call the controller method
        ResponseEntity<ImpressionsViewsResponse> responseEntity = videoController.getTotalImpressionsAndViews(1L);

        // Verify that the controller returns a 200 OK status code
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Verify that the controller returns the expected response
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
