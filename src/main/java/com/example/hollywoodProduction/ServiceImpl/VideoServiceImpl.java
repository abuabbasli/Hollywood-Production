package com.example.hollywoodProduction.ServiceImpl;


import com.example.hollywoodProduction.Entity.User;
import com.example.hollywoodProduction.Entity.UserInteraction;
import com.example.hollywoodProduction.Entity.UserInteraction.InteractionType;
import com.example.hollywoodProduction.Entity.Video;

import com.example.hollywoodProduction.Repository.UserInteractionRepository;
import com.example.hollywoodProduction.Repository.UserRepository;
import com.example.hollywoodProduction.Repository.VideoRepository;
import com.example.hollywoodProduction.Response.ImpressionsViewsResponse;
import com.example.hollywoodProduction.Services.VideoService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Date;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final EntityManager entityManager;


    private final UserInteractionRepository userInteractionRepository;

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);


    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, UserInteractionRepository userInteractionRepository, UserRepository userRepository, EntityManager entityManager) {
        this.videoRepository = videoRepository;
        this.userInteractionRepository = userInteractionRepository;
        this.userRepository = userRepository;
        this.entityManager=entityManager;
    }

    @Override
    @Transactional
    public List<Video> searchByAttribute(String attributeName, String searchValue) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Video> cq = cb.createQuery(Video.class);

        Root<Video> video = cq.from(Video.class);

        // Convert both the column value and the search value to lowercase
        Predicate predicate = cb.like(cb.lower(video.get(attributeName)), "%" + searchValue.toLowerCase() + "%");
        cq.where(predicate);

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Video saveVideo(Video video) {
        if (video == null) {
            throw new IllegalArgumentException("Video parameter is null");
        }
        logger.info("Saving video: {}", video.getTitle());
        return videoRepository.save(video);
    }

    @Override
    public Video getVideoById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Video not found with id: {}", id);
                    return new RuntimeException("Video not found");
                });
    }

    @Override
    public List<Video> getAllVideos() {
        List<Video> videos = videoRepository.findAll();
        logger.info("Retrieved {} videos", videos.size());
        return videos;
    }

    @Override
    public Video updateVideo(Video updatedVideo, Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Video not found with id: {}", id);
                    return new RuntimeException("Video not found");
                });

        // Update video properties
        if (updatedVideo.getTitle() != null) {
            video.setTitle(updatedVideo.getTitle());
        }
        if (updatedVideo.getMainActor() != null) {
            video.setMainActor(updatedVideo.getMainActor());
        }
        if (updatedVideo.getSynopsis() != null) {
            video.setSynopsis(updatedVideo.getSynopsis());
        }
        if (updatedVideo.getDirector() != null) {
            video.setDirector(updatedVideo.getDirector());
        }
        if (updatedVideo.getVideoCast() != null) {
            video.setVideoCast(updatedVideo.getVideoCast());
        }
        if (updatedVideo.getYearOfRelease() != null) {
            video.setYearOfRelease(updatedVideo.getYearOfRelease());
        }
        if (updatedVideo.getGenre() != null) {
            video.setGenre(updatedVideo.getGenre());
        }
        if (updatedVideo.getRunningTime() != null) {
            video.setRunningTime(updatedVideo.getRunningTime());
        }

        logger.info("Updating video with id {}: {}", id, video.getTitle());
        return videoRepository.save(video);
    }

    @Override
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
        logger.info("Deleted video with id: {}", id);
    }



    @Override
    public Video publishVideo(Video video) {

        return videoRepository.save(video);
    }

    @Override
    public Video editVideo(Long id, Video videoDetails) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Video not found with id: {}", id);
                    return new RuntimeException("Video not found with id: " + id);
                });

        // Update video metadata
        if (videoDetails.getTitle() != null) {
            video.setTitle(videoDetails.getTitle());
        }
        if (videoDetails.getMainActor() != null) {
            video.setMainActor(videoDetails.getMainActor());
        }
        if (videoDetails.getSynopsis() != null) {
            video.setSynopsis(videoDetails.getSynopsis());
        }
        if (videoDetails.getDirector() != null) {
            video.setDirector(videoDetails.getDirector());
        }
        if (videoDetails.getVideoCast() != null) {
            video.setVideoCast(videoDetails.getVideoCast());
        }
        if (videoDetails.getYearOfRelease() != null) {
            video.setYearOfRelease(videoDetails.getYearOfRelease());
        }
        if (videoDetails.getGenre() != null) {
            video.setGenre(videoDetails.getGenre());
        }



        logger.info("Editing video with id {}: {}", id, video.getTitle());
        return videoRepository.save(video);
    }

    @Override
    public void delistVideo(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Video not found with id: {}", id);
                    return new RuntimeException("Video not found with id: " + id);
                });

        video.setIsActive(false);
        videoRepository.save(video);
        logger.info("Delisting video with id: {}", id);
    }

    @Override
    public Video loadVideo(Long userId, Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> {
                    logger.error("Video not found with id: {}", videoId);
                    return new RuntimeException("Video not found with id: " + videoId);
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new RuntimeException("User not found with id: " + userId);
                });

        recordInteraction(user, video, InteractionType.IMPRESSION);
        logger.info("Loading video with id {} for user with id {}", videoId, userId);
        return video;
    }

    @Override
    public String playVideo(Long userId, Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> {
                    logger.error("Video not found with id: {}", videoId);
                    return new RuntimeException("Video not found with id: " + videoId);
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new RuntimeException("User not found with id: " + userId);
                });

        recordInteraction(user, video, InteractionType.VIEW);
        logger.info("User with id {} played video with id {}", userId, videoId);
        return video.getS3ObjectKey();
    }


    @Override
    public List<Video> listAllVideos() {
        List<Video> videos = videoRepository.findAll();
        logger.info("Retrieved {} videos", videos.size());
        return videos;
    }

    @Override
    public ImpressionsViewsResponse getTotalImpressionsAndViews(Long videoId) {
        try {
            ImpressionsViewsResponse response = videoRepository.getTotalImpressionsAndViews(videoId);
            if (response == null) {
                logger.error("TotalImpressionsAndViews response is null for videoId: {}", videoId);
                throw new RuntimeException("TotalImpressionsAndViews response is null");
            }
            logger.info("Retrieved ImpressionsViewsResponse for videoId: {}", videoId);
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving ImpressionsViewsResponse for videoId: {}", videoId, e);
            throw new RuntimeException("Error occurred while retrieving ImpressionsViewsResponse for videoId: " + videoId, e);
        }
    }


   private void recordInteraction(User user, Video video, InteractionType interactionType) {
        UserInteraction interaction = new UserInteraction();
        interaction.setUser(user);
        interaction.setVideo(video);
        interaction.setInteractionType(interactionType);
        interaction.setTimestamp(new Date());

        userInteractionRepository.save(interaction);
    }
    }



