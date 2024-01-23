package com.example.hollywoodProduction.Repository;


import com.example.hollywoodProduction.Entity.Video;
import com.example.hollywoodProduction.Response.ImpressionsViewsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoRepository extends JpaRepository<Video, Long>, JpaSpecificationExecutor<Video> {

    @Query("SELECT NEW com.example.hollywoodProduction.Response.ImpressionsViewsResponse(" +
            "SUM(CASE WHEN ui.interactionType = 'IMPRESSION' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN ui.interactionType = 'VIEW' THEN 1 ELSE 0 END)) " +
            "FROM UserInteraction ui WHERE ui.video.id = :videoId")
    ImpressionsViewsResponse getTotalImpressionsAndViews(Long videoId);



}
