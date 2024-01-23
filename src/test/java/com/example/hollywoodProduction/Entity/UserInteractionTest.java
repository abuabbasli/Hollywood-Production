package com.example.hollywoodProduction.Entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserInteractionTest {
    private UserInteraction userInteraction;
    private User user;
    private Video video;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("testUser");

        video = new Video();
        video.setId(1L);
        video.setTitle("Test Video");

        userInteraction = new UserInteraction();
        userInteraction.setId(1L);
        userInteraction.setUser(user);
        userInteraction.setVideo(video);
        userInteraction.setInteractionType(UserInteraction.InteractionType.IMPRESSION);
    }

    @Test
    public void testCreateUserInteraction() {
        assertNotNull(userInteraction);
        assertEquals(1L, userInteraction.getId());
        assertEquals(user, userInteraction.getUser());
        assertEquals(video, userInteraction.getVideo());
        assertEquals(UserInteraction.InteractionType.IMPRESSION, userInteraction.getInteractionType());
    }

    @Test
    public void testUpdateUserInteraction() {
        // Modify user interaction data
        userInteraction.setInteractionType(UserInteraction.InteractionType.VIEW);

        assertEquals(UserInteraction.InteractionType.VIEW, userInteraction.getInteractionType());
    }

    @Test
    public void testToStringMethod() {
        // Check that the toString method does not result in a NullPointerException
        assertNotNull(userInteraction.toString());
    }
}
