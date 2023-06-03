package org.example.category;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Category {
    private final String title;
    private int sum;
    public static final String DEFAULT_CATEGORY_TITLE = "другое";
    private List<Purchase> purchases = new ArrayList<>();

    public Category(String title) {
        this.title = title;
    }

    public Category(
            @JsonProperty("title")String title,
            @JsonProperty("sum") int sum,
            @JsonProperty("purchases") List<Purchase> purchases) {
        this.title = title;
        this.sum = sum;
        this.purchases = purchases;
    }


    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || obj.getClass() != this.getClass()) return false;

        final Category newObj = (Category) obj;
        return (this.getTitle().equals(newObj.getTitle()) && newObj.getTitle() != null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
//        return 31* (title != null ? title.hashCode() : 0);
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder();
        sb.append("Category{ " + "title = '")
                .append(title).append('\'')
                .append(", sum = ")
                .append(sum).append(", purchases = ");

        purchases.forEach(str -> sb.append(str).append(" "));
        sb.append('}');

        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
