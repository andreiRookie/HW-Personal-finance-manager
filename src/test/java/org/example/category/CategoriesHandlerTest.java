package org.example.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

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
        // TODO
    }

    private HashSet<Category> getCategories() {
        HashSet<Category> set =new HashSet<>();
        int sum = 0;
        
        Category food = new Category(FOOD);
        food.setSum(sum += 100);
        Category clothes = new Category(CLOTHES);
        clothes.setSum(sum += 100);
        Category home = new Category(Home);
        home.setSum(sum += 100);
        Category car = new Category(CAR);
        car.setSum(sum += 100);

        set.add(food);
        set.add(clothes);
        set.add(home);
        set.add(car);

        return set;
    }
}