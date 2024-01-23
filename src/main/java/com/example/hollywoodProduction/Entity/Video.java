package com.example.hollywoodProduction.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "videos")
@Getter @Setter
@NoArgsConstructor
@ToString
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "mainActor")
    private String mainActor;
    @Lob // For the long text content
    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "director")
    private String director;

    @Column(name = "videoCast")
    private String videoCast;

    @Column(name = "year_of_release")
    private Integer yearOfRelease;

    @Column(name = "genre")
    private String genre;

    @Column(name = "running_time")
    private Integer runningTime;

    @Column(name = "s3_object_key")
    private String s3ObjectKey;

    @Column(name = "is_active")
    private Boolean isActive;

    @Override
    public String toString() {
        return "Video{" +
                "title='" + title + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", director='" + director + '\'' +
                ", cast='" + videoCast + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", genre='" + genre + '\'' +
                ", runningTime=" + runningTime +
                '}';
    }



}
