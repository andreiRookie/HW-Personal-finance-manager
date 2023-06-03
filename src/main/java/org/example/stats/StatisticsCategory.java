package org.example.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatisticsCategory {
    private final String title;
    private final int sum;

    public StatisticsCategory(
            @JsonProperty("category") String title,
            @JsonProperty("sum") int sum
    ) {
        this.title = title;
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public int getSum() {
        return sum;
    }
}
