package org.example.category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CategoriesHandler {
    private final HashSet<Category> categories;

    public CategoriesHandler(HashSet<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public Category getMaxCategory() {
        int maxSum = 0;
        Category result = null;

        for (Category category : categories) {
            if (category.getSum() > maxSum) {
                maxSum = category.getSum();
                result = category;
            }
        }
        return result;
    }

    public Category getMaxYearCategory(String yyyy) {
        Category result = null;
        int maxYearSum = 0;
        int currYearPurchasesSum = 0;
        for (Category category : categories) {

            try {

                for (Purchase purchase : category.getPurchases()) {
                    if (!purchase.getDate().equals("")) {
                        String currYear = purchase.getDate().substring(0, 4);
                        if (currYear.equals(yyyy)) {
                            currYearPurchasesSum += purchase.getPrice();
                        }
                    }
                }

                if (currYearPurchasesSum > maxYearSum) {
                    maxYearSum = currYearPurchasesSum;
                    result = category;
                }
                currYearPurchasesSum = 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Category getMaxMonthCategory(String mm) {
        Category result = null;
        int maxMonthSum = 0;
        int currMonthPurchasesSum = 0;
        for (Category category : categories) {

            try {
                for (Purchase purchase : category.getPurchases()) {
                    if (!purchase.getDate().equals("")) {
                        String currMonth = purchase.getDate().substring(5, 7);
                        if (currMonth.equals(mm)) {
                            currMonthPurchasesSum += purchase.getPrice();
                        }
                    }
                }

                if (currMonthPurchasesSum > maxMonthSum) {
                    maxMonthSum = currMonthPurchasesSum;
                    result = category;
                }
                currMonthPurchasesSum = 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Category getMaxDayCategory(String dd) {
        Category result = null;
        int maxDaySum = 0;
        int currDayPurchasesSum = 0;
        for (Category category : categories) {

            try {
                for (Purchase purchase : category.getPurchases()) {
                    if (!purchase.getDate().equals("")) {
                        String currDay = purchase.getDate().substring(8);
                        if (currDay.equals(dd)) {
                            currDayPurchasesSum += purchase.getPrice();
                        }
                    }
                }

                if (currDayPurchasesSum > maxDaySum) {
                    maxDaySum = currDayPurchasesSum;
                    result = category;
                }
                currDayPurchasesSum = 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public HashSet<Category> getCategories() {
        return categories;
    }

    public Category getCategoryByTitle(String title) {
        for (Category category : categories) {
            if (category.getTitle().equals(title)) {
                return category;
            }
        }
        return null;
    }

    public List<Purchase> getAllPurchasesList() {
        List<Purchase> list = new ArrayList<>();
        for (Category category : getCategories()) {
            list.addAll(category.getPurchases());
        }
        return list;
    }

    public List<String> getAllPurchasesTitleList() {
        List<String> list = new ArrayList<>();
        for (Category category : getCategories()) {
            for (Purchase purchase : category.getPurchases()) {
                list.add(purchase.getTitle());
            }
        }
        return list;
    }
}
