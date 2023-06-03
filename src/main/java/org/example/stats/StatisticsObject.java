package org.example.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.example.category.Category;
import java.util.HashSet;
@JsonRootName("statistics")
public class StatisticsObject {

    private final HashSet<Category> categories;
    private final StatisticsCategory maxCategory;
    private final StatisticsCategory maxYearCategory;
    private final StatisticsCategory maxMonthCategory;
    private final StatisticsCategory maxDayCategory;

    public StatisticsObject(
            @JsonProperty("categories")HashSet<Category> categories,
            @JsonProperty("maxCategory") StatisticsCategory maxCategory,
            @JsonProperty("maxYearCategory") StatisticsCategory maxYearCategory,
            @JsonProperty("maxMonthCategory") StatisticsCategory maxMonthCategory,
            @JsonProperty("maxDayCategory") StatisticsCategory maxDayCategory
    ) {
        this.categories = categories;
        this.maxCategory = maxCategory;
        this.maxYearCategory = maxYearCategory;
        this.maxMonthCategory = maxMonthCategory;
        this.maxDayCategory = maxDayCategory;
    }

    public HashSet<Category> getCategories() {
        return categories;
    }

    public StatisticsCategory getMaxCategory() {
        return maxCategory;
    }

    public StatisticsCategory getMaxYearCategory() {
        return maxYearCategory;
    }

    public StatisticsCategory getMaxMonthCategory() {
        return maxMonthCategory;
    }

    public StatisticsCategory getMaxDayCategory() {
        return maxDayCategory;
    }
}
