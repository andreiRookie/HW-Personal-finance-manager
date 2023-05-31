package org.example.net;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("maxCategory")
public class Response {
    private final String category;
    private final int sum;

    public Response(
            @JsonProperty("category") String category,
            @JsonProperty("sum") int sum) {
        this.category = category;
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Response{" +
                "category='" + category + '\'' +
                ", sum=" + sum +
                '}';
    }
}

