package org.example.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoriesHandlerTest {

    CategoriesHandler categoriesHandler;
    final String FOOD = "food";
    final String CLOTHES = "clothes";
    final String Home = "home";
    final String CAR = "car";
    final String SERVICE = "service";

    @BeforeEach
    void init() {
        categoriesHandler = new CategoriesHandler(getCategories());
    }

    @Test
    void getMaxSumCategory() {

        Category expected = new Category(CAR);
        expected.setSum(400);
        Category actual = categoriesHandler.getMaxSumCategory();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getCategoryByTitle_returnsCategory() {
        Category expected = new Category(CLOTHES);
        Category actual = categoriesHandler.getCategoryByTitle(CLOTHES);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getCategoryByTitle_returnsNull() {
        Category actual = categoriesHandler.getCategoryByTitle(SERVICE);

        assertNull(actual);
    }

    @Test
    void getAllPurchasesList() {
        List<String> expected = new ArrayList<>() {
            {
                add("shirt");
                add("oil");
                add("bread");
                add("butter");
                add("soap");
            }
        };
        List<String> actual = categoriesHandler.getAllPurchasesList();

        Assertions.assertEquals(expected, actual);
    }

    private HashSet<Category> getCategories() {
        HashSet<Category> set = new HashSet<>();
        int sum = 0;

        Category food = new Category(FOOD);
        food.setSum(sum += 100);
        food.addPurchase("bread");
        food.addPurchase("butter");
        Category clothes = new Category(CLOTHES);
        clothes.setSum(sum += 100);
        clothes.addPurchase("shirt");
        Category home = new Category(Home);
        home.setSum(sum += 100);
        home.addPurchase("soap");
        Category car = new Category(CAR);
        car.setSum(sum += 100);
        car.addPurchase("oil");

        set.add(food);
        set.add(clothes);
        set.add(home);
        set.add(car);

        return set;
    }
}