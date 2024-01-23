package com.example.hollywoodProduction.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ImpressionsViewsResponse {
    private Long totalImpressions;
    private Long totalViews;

    public ImpressionsViewsResponse(Long totalImpressions, Long totalViews) {
        this.totalImpressions = totalImpressions;
        this.totalViews = totalViews;
    }
}
