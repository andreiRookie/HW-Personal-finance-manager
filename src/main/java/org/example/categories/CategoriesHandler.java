package org.example.categories;

import java.util.Collection;
import java.util.HashSet;

public class CategoriesHandler {

    private final HashSet<Category> categories = new HashSet<>();

    public CategoriesHandler() {
//        this.categories.add(new Category(Category.DEFAULT_CATEGORY_TITLE));
    }

    public CategoriesHandler(Collection<Category> categories) {
        this.categories.addAll(categories);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

//    public Response getMaxSum() {
//        int maxSum = 0;
//        String categoryTitle = "";
//        for (Category category : categories) {
//            if (category.getSum() > maxSum) {
//                maxSum = category.getSum();
//                categoryTitle = category.getTitle();
//            }
//        }
//        return new Response(categoryTitle, maxSum);
////        return maxSum;
//    }

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
        for (Category cat : categories) {
            if (cat.getTitle().equals(title)) {
                return cat;
//                for (Category category : categories) {
//                    if (category.getTitle().equals(title)) {
//                        return category;
//                    }
//                }
            }
        }
        return null;
    }
}
