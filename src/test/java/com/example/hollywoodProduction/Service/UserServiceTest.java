package com.example.hollywoodProduction.Service;

import com.example.hollywoodProduction.Entity.User;
import com.example.hollywoodProduction.Entity.UserInteraction;
import com.example.hollywoodProduction.Entity.UserInteraction.InteractionType;
import com.example.hollywoodProduction.Entity.Video;
import com.example.hollywoodProduction.Repository.UserInteractionRepository;
import com.example.hollywoodProduction.Repository.UserRepository;
import com.example.hollywoodProduction.Repository.VideoRepository;
import com.example.hollywoodProduction.Response.ImpressionsViewsResponse;
import com.example.hollywoodProduction.Services.UserService;
import com.example.hollywoodProduction.Services.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class UserServiceTest {


    @Mock
    private VideoRepository videoRepository;
    @Mock
    private UserInteractionRepository userInteractionRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @InjectMocks
    private VideoService videoService;


    @Mock
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.addUser(user);
        assertNotNull(createdUser);
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User();
        User updatedDetails = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedDetails);
        User updatedUser = userService.updateUser(1L, updatedDetails);
        assertNotNull(updatedUser);
        verify(userRepository).save(existingUser);
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        verify(userRepository).delete(user);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User foundUser = userService.getUserById(1L);
        assertNotNull(foundUser);
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);
        List<User> foundUsers = userService.getAllUsers();
        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        verify(userRepository).findAll();
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, new User()));
    }


    @Test
    void testSearchByAttribute() {
        String attributeName = "title";
        String searchValue = "test";
        List<Video> expectedVideos = Arrays.asList(new Video(), new Video());

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<Video> cq = mock(CriteriaQuery.class);
        Root<Video> videoRoot = mock(Root.class);
        TypedQuery<Video> query = mock(TypedQuery.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Video.class)).thenReturn(cq);
        when(cq.from(Video.class)).thenReturn(videoRoot);
        when(entityManager.createQuery(cq)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedVideos);

        List<Video> result = videoService.searchByAttribute(attributeName, searchValue);

        assertEquals(expectedVideos, result);
    }

    @Test
    void testSaveVideo() {
        Video video = new Video();
        when(videoRepository.save(video)).thenReturn(video);
        Video savedVideo = videoService.saveVideo(video);
        assertEquals(video, savedVideo);
    }

    @Test
    void testGetVideoById() {
        Long videoId = 1L;
        Video video = new Video();
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        Video foundVideo = videoService.getVideoById(videoId);
        assertEquals(video, foundVideo);
    }

    @Test
    void testGetAllVideos() {
        List<Video> videos = Arrays.asList(new Video(), new Video());
        when(videoRepository.findAll()).thenReturn(videos);
        List<Video> result = videoService.getAllVideos();
        assertEquals(videos, result);
    }

    @Test
    void testUpdateVideo() {
        Long videoId = 1L;
        Video existingVideo = new Video();
        Video updatedVideo = new Video();
        updatedVideo.setTitle("New Title");

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(existingVideo));
        when(videoRepository.save(existingVideo)).thenReturn(updatedVideo);

        Video result = videoService.updateVideo(updatedVideo, videoId);
        assertEquals(updatedVideo, result);
    }

    @Test
    void testDeleteVideo() {
        Long videoId = 1L;
        doNothing().when(videoRepository).deleteById(videoId);
        videoService.deleteVideo(videoId);
        verify(videoRepository).deleteById(videoId);
    }

    @Test
    void testPublishVideo() {
        Video video = new Video();
        when(videoRepository.save(video)).thenReturn(video);
        Video publishedVideo = videoService.publishVideo(video);
        assertEquals(video, publishedVideo);
    }

    @Test
    void testEditVideo() {
        Long videoId = 1L;
        Video existingVideo = new Video();
        Video updatedDetails = new Video();
        updatedDetails.setTitle("Updated Title");

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(existingVideo));
        when(videoRepository.save(existingVideo)).thenReturn(updatedDetails);

        Video result = videoService.editVideo(videoId, updatedDetails);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void testDelistVideo() {
        Long videoId = 1L;
        Video video = new Video();
        video.setIsActive(true);

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        videoService.delistVideo(videoId);

        assertFalse(video.getIsActive());
        verify(videoRepository).save(video);
    }

    @Test
    void testLoadVideo() {
        Long userId = 1L;
        Long videoId = 1L;
        Video video = new Video();
        User user = new User();

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Video result = videoService.loadVideo(userId, videoId);

        assertEquals(video, result);
        verify(userInteractionRepository).save(any(UserInteraction.class));
    }

    @Test
    void testPlayVideo() {
        Long userId = 1L;
        Long videoId = 1L;
        Video video = new Video();
        video.setS3ObjectKey("videoKey");
        User user = new User();

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        String s3ObjectKey = videoService.playVideo(userId, videoId);

        assertEquals("videoKey", s3ObjectKey);
        verify(userInteractionRepository).save(any(UserInteraction.class));
    }

    @Test
    void testListAllVideos() {
        List<Video> videos = Arrays.asList(new Video(), new Video());
        when(videoRepository.findAll()).thenReturn(videos);
        List<Video> result = videoService.listAllVideos();
        assertEquals(videos, result);
    }

    @Test
    void testGetTotalImpressionsAndViews() {
        Long videoId = 1L;
        ImpressionsViewsResponse response = new ImpressionsViewsResponse(10L, 5L);
        when(videoRepository.getTotalImpressionsAndViews(videoId)).thenReturn(response);

        ImpressionsViewsResponse result = videoService.getTotalImpressionsAndViews(videoId);

        assertNotNull(result);
        assertEquals(10, result.getTotalImpressions());
        assertEquals(5, result.getTotalViews());
    }

}
