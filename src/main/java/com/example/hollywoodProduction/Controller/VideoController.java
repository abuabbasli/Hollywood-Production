package com.example.hollywoodProduction.Controller;

import com.example.hollywoodProduction.Entity.Video;
import com.example.hollywoodProduction.Response.ImpressionsViewsResponse;
import com.example.hollywoodProduction.Services.VideoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;


import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);


    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    @Operation(summary = "Publish a new video")
    public ResponseEntity<Video> publishVideo(@Parameter(description = "Video object to be published", required = true) @RequestBody Video video) {
        try {
            if (video == null) {
                return ResponseEntity.badRequest().build();
            }
            Video publishedVideo = videoService.publishVideo(video);
            if (publishedVideo == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error publishing video");
            }
            return ResponseEntity.ok(publishedVideo);
        } catch (Exception e) {
            logger.error("Error publishing video: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error publishing video", e);
        }
    }
    @PutMapping("/{id}/edit")
    @Operation(summary = "Edit an existing video")
    public ResponseEntity<Video> editVideo(@Parameter(description = "ID of the video to be edited", required = true) @PathVariable Long id,
                                           @Parameter(description = "Updated video object", required = true) @RequestBody Video video) {
        try {
            if (video == null) {
                return ResponseEntity.badRequest().build();
            }
            Video editedVideo = videoService.editVideo(id, video);
            if (editedVideo == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error editing video");
            }
            return ResponseEntity.ok(editedVideo);
        } catch (EntityNotFoundException e) {
            logger.error("Video not found with ID " + id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error editing video with ID " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error editing video", e);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing video")
    public ResponseEntity<Video> updateVideo(@Parameter(description = "ID of the video to be updated", required = true) @PathVariable Long id,
                                             @Parameter(description = "Updated video object", required = true) @RequestBody Video video) {
        try {
            if (video == null) {
                return ResponseEntity.badRequest().build();
            }
            Video updatedVideo = videoService.updateVideo(video, id);
            if (updatedVideo == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating video");
            }
            return ResponseEntity.ok(updatedVideo);
        } catch (EntityNotFoundException e) {
            logger.error("Video not found with ID " + id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error updating video with ID " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating video", e);
        }
    }

    @PostMapping("/video/{id}")
    @Operation(summary = "Delist a video (soft delete)")
    public ResponseEntity<Void> delistVideo(@Parameter(description = "ID of the video to be delisted", required = true) @PathVariable Long id) {
        try {
            videoService.delistVideo(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            logger.error("Video not found with ID " + id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error delisting video with ID " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error delisting video", e);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "View a specific video by ID")
    public ResponseEntity<Video> getVideoById(@Parameter(description = "ID of the video to be viewed", required = true) @PathVariable Long id) {
        try {
            Video video = videoService.getVideoById(id);
            if (video == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found with ID " + id);
            }
            return ResponseEntity.ok(video);
        } catch (Exception e) {
            logger.error("Error retrieving video with ID " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving video", e);
        }
    }

    @GetMapping("/list")
    @Operation(summary = "View a list of available videos")
    public ResponseEntity<List<Video>> listAllVideos() {
        try {
            List<Video> videos = videoService.listAllVideos();
            if (videos == null || videos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
            return ResponseEntity.ok(videos);
        } catch (Exception e) {
            logger.error("Error retrieving all videos: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving videos", e);
        }
    }

    @Operation(summary = "Search for videos based on a specific attribute and value",
            description = "Provide an attribute name and a value to search for videos. " +
                    "Example attributes: title, genre, yearOfRelease, cast, director, mainActor.")
    @GetMapping("/search")
    public ResponseEntity<List<Video>> searchVideos(
            @Parameter(description = "Name of the attribute to search by. " +
                    "Possible values: title, genre, yearOfRelease, cast, director, mainActor")
            @RequestParam String attribute,

            @Parameter(description = "Value of the attribute to search for")
            @RequestParam String value) {
        try {
            List<Video> results = videoService.searchByAttribute(attribute, value);
            if (results == null || results.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            logger.error("Error searching videos by attribute: " + attribute, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error searching videos", e);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a video (hard delete)")
    public ResponseEntity<?> deleteVideo(@Parameter(description = "ID of the video to be deleted", required = true) @PathVariable Long id) {
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            logger.error("Video not found with ID " + id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error deleting video with ID " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting video", e);
        }
    }

    @GetMapping
    @Operation(summary = "List all available videos")
    public ResponseEntity<List<Video>> getAllVideos() {
        try {
            List<Video> videos = videoService.getAllVideos();
            if (videos == null || videos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
            return ResponseEntity.ok(videos);
        } catch (Exception e) {
            logger.error("Error retrieving all videos: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving videos", e);
        }
    }

    @GetMapping("/{userId}/load/{videoId}")
    @Operation(summary = "Load a video for viewing")
    public ResponseEntity<Video> loadVideo(@Parameter(description = "User ID", required = true) @PathVariable Long userId,
                                           @Parameter(description = "Video ID", required = true) @PathVariable Long videoId) {
        try {
            Video video = videoService.loadVideo(userId, videoId);
            if (video == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found for user " + userId + " and video ID " + videoId);
            }
            return ResponseEntity.ok(video);
        } catch (Exception e) {
            logger.error("Error loading video for user " + userId + " and video ID " + videoId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading video", e);
        }
    }

    @GetMapping("/{userId}/play/{videoId}")
    @Operation(summary = "Play a video")
    public ResponseEntity<String> playVideo(@Parameter(description = "User ID", required = true) @PathVariable Long userId,
                                            @Parameter(description = "Video ID", required = true) @PathVariable Long videoId) {
        try {
            String s3ObjectKey = videoService.playVideo(userId, videoId);
            if (s3ObjectKey == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found for user " + userId + " and video ID " + videoId);
            }
            return ResponseEntity.ok(s3ObjectKey);
        } catch (Exception e) {
            logger.error("Error playing video for user " + userId + " and video ID " + videoId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error playing video", e);
        }
    }
    @GetMapping("/{videoId}/impressions-views")
    @Operation(summary = "Get total impressions and views for a video")
    public ResponseEntity<ImpressionsViewsResponse> getTotalImpressionsAndViews(
            @Parameter(description = "ID of the video", required = true) @PathVariable Long videoId) {
        try {
            ImpressionsViewsResponse impressionsViewsResponse = videoService.getTotalImpressionsAndViews(videoId);
            if (impressionsViewsResponse == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Impressions and views not found for video with ID: " + videoId);
            }
            return ResponseEntity.ok(impressionsViewsResponse);
        } catch (EntityNotFoundException e) {
            logger.error("Video not found with ID " + videoId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error getting total impressions and views for video with ID " + videoId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting total impressions and views for video", e);
        }
    }

}
