package org.example.category;

import java.util.HashSet;
import java.util.Objects;

public class Category {
    private final String title;
    private int sum;
    public static final String DEFAULT_CATEGORY_TITLE = "другое";
    private final HashSet<String> purchases = new HashSet<>();

    public Category(String title) {
        this.title = title;
    }

    public void addPurchase(String purchase) {
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

    public HashSet<String> getPurchases() {
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
}
