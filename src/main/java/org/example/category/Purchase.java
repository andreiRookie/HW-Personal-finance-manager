package org.example.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Purchase {
    private final String title;
    private final String date;
    private final int price;

    public Purchase(
            @JsonProperty("title") String title,
            @JsonProperty("date")String date,
            @JsonProperty("sum")int sum
    ) {
        this.title = title;
        this.date = date;
        this.price = sum;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", sum=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return price == purchase.price
                && Objects.equals(title, purchase.title)
                && Objects.equals(date, purchase.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, price);
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }
}
