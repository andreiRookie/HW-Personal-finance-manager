package org.example.category;

import java.util.HashSet;

public class CategoriesHandler {

    private final HashSet<Category> categories;

    public CategoriesHandler(HashSet<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public Category getMaxSumCategory() {
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
}
