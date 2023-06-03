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
    void getMaxCategory() {

        Category expected = new Category(CAR);
        expected.setSum(400);
        Category actual = categoriesHandler.getMaxCategory();

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
        List<Purchase> expected = new ArrayList<>() {
            {
                add(new Purchase("shirt", "", 0));
                add(new Purchase("oil", "", 0));
                add(new Purchase("bread", "", 0));
                add(new Purchase("butter", "", 0));
                add(new Purchase("soap", "", 0));
            }
        };
        List<Purchase> actual = categoriesHandler.getAllPurchasesList();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllPurchasesTitleList() {
        //TODO
    }

    private HashSet<Category> getCategories() {
        HashSet<Category> set = new HashSet<>();
        int sum = 0;

        Category food = new Category(FOOD);
        food.setSum(sum += 100);
        food.addPurchase(new Purchase("bread", "", 0));
        food.addPurchase(new Purchase("butter", "", 0));
        Category clothes = new Category(CLOTHES);
        clothes.setSum(sum += 100);
        clothes.addPurchase(new Purchase("shirt", "", 0));
        Category home = new Category(Home);
        home.setSum(sum += 100);
        home.addPurchase(new Purchase("soap", "", 0));
        Category car = new Category(CAR);
        car.setSum(sum += 100);
        car.addPurchase(new Purchase("oil", "", 0));

        set.add(food);
        set.add(clothes);
        set.add(home);
        set.add(car);

        return set;
    }
}