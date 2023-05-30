package org.example.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.example.categories.Category;

@JsonRootName("maxCategory")
public class Response {
//    private final String maxCategory;
//    private Category сategory;
    private final String category;
    private final int sum;

//    public Response(String maxCategory, String category, int sum) {
    public Response(
            @JsonProperty("category") String category,
            @JsonProperty("sum") int sum) {

//    this.maxCategory = maxCategory;
        this.category = category;
        this.sum = sum;
    }

//    public String getMaxCategory() {
//        return maxCategory;
//    }

    public String getCategory() {
        return category;
    }

    public int getSum() {
        return sum;
    }
}

