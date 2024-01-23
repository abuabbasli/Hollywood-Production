package com.example.hollywoodProduction.Entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VideoTest {

    private Video video;

    @BeforeEach
    public void setUp() {
        video = new Video();
    }

    @Test
    public void testIdGetterAndSetter() {
        Long id = 1L;
        video.setId(id);
        assertEquals(id, video.getId());
    }

    @Test
    public void testTitleGetterAndSetter() {
        String title = "Sample Video";
        video.setTitle(title);
        assertEquals(title, video.getTitle());
    }

    @Test
    public void testMainActorGetterAndSetter() {
        String mainActor = "John Doe";
        video.setMainActor(mainActor);
        assertEquals(mainActor, video.getMainActor());
    }

    @Test
    public void testSynopsisGetterAndSetter() {
        String synopsis = "This is a test synopsis.";
        video.setSynopsis(synopsis);
        assertEquals(synopsis, video.getSynopsis());
    }

    @Test
    public void testDirectorGetterAndSetter() {
        String director = "Jane Smith";
        video.setDirector(director);
        assertEquals(director, video.getDirector());
    }

    @Test
    public void testVideoCastGetterAndSetter() {
        String videoCast = "Actor1, Actor2";
        video.setVideoCast(videoCast);
        assertEquals(videoCast, video.getVideoCast());
    }

    @Test
    public void testYearOfReleaseGetterAndSetter() {
        Integer yearOfRelease = 2022;
        video.setYearOfRelease(yearOfRelease);
        assertEquals(yearOfRelease, video.getYearOfRelease());
    }

    @Test
    public void testGenreGetterAndSetter() {
        String genre = "Action";
        video.setGenre(genre);
        assertEquals(genre, video.getGenre());
    }

    @Test
    public void testRunningTimeGetterAndSetter() {
        Integer runningTime = 120;
        video.setRunningTime(runningTime);
        assertEquals(runningTime, video.getRunningTime());
    }

    @Test
    public void testS3ObjectKeyGetterAndSetter() {
        String s3ObjectKey = "video/123.mp4";
        video.setS3ObjectKey(s3ObjectKey);
        assertEquals(s3ObjectKey, video.getS3ObjectKey());
    }

    @Test
    public void testIsActiveGetterAndSetter() {
        Boolean isActive = true;
        video.setIsActive(isActive);
        assertEquals(isActive, video.getIsActive());
    }

    @Test
    public void testToString() {
        video.setTitle("Sample Video");
        video.setSynopsis("This is a test synopsis.");
        video.setDirector("Jane Smith");
        video.setVideoCast("Actor1, Actor2");
        video.setYearOfRelease(2022);
        video.setGenre("Action");
        video.setRunningTime(120);

        String expectedToString = "Video{title='Sample Video', synopsis='This is a test synopsis.', director='Jane Smith', cast='Actor1, Actor2', yearOfRelease=2022, genre='Action', runningTime=120}";
        assertEquals(expectedToString, video.toString());
    }
}
