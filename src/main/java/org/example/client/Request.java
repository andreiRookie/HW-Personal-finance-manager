package org.example.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    private final String title;
    private final String date;
    private final int sum;

    public Request(
            @JsonProperty("title") String title,
            @JsonProperty("date") String date,
            @JsonProperty("sum") int sum) {
        this.title = title;
        this.date = date;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Request{" +
                "purchaseTitle = '" + title + '\'' +
                ", date = '" + date + '\'' +
                ", sum = " + sum +
                '}';
    }

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public int getSum() {
        return sum;
    }
}
