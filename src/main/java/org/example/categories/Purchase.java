package org.example.categories;

public class Purchase {
    private String title;
    private String date;
    private int sum;

    public Purchase(String title){
        this.title = title;
    }

    public Purchase( String title,String date, int sum) {
        this.title = title;
        this.date = date;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "title = '" + title + '\'' +
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
