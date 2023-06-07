package org.example.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNull;

class CategoriesHandlerTest {

    CategoriesHandler categoriesHandler;
    final String FOOD = "food";
    final String CLOTHES = "clothes";
    final String HOME = "home";
    final String CAR = "car";
    final String SERVICE = "service";
    final int MAX_SUM = 2000;
    final int MAX_DAY_SUM = 400;
    final String DAY = "11";
    final int MAX_MONTH_SUM = 1400;
    final String MONTH = "02";
    final int MAX_YEAR_SUM = 500;
    final String YEAR = "2022";

    @BeforeEach
    void init() {
        categoriesHandler = new CategoriesHandler(getCategories());
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
        List<Purchase> expected = new ArrayList<>(
                Arrays.asList(new Purchase("shirt", "2023.02.28", 1400),
                        new Purchase("oil", "2020.09.29", 2000),
                        new Purchase("bread", "2022.07.08", 200),
                        new Purchase("butter", "2022.01.12", 300),
                        new Purchase("soap", "2021.11.11", 400))
        );

        List<Purchase> actual = categoriesHandler.getAllPurchasesList();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllPurchasesList_notEvergreen() {
        List<Purchase> expected = new ArrayList<>(
                Arrays.asList(new Purchase("shirt", "2023.02.28", 12),
                        new Purchase("oil", "2020.09.29", 2000),
                        new Purchase("bread", "2022.07.08", 200),
                        new Purchase("butter", "2022.01.12", 300),
                        new Purchase("soap", "", 400))
        );

        List<Purchase> actual = categoriesHandler.getAllPurchasesList();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllPurchasesTitleList() {
        List<String> expected = new ArrayList<>(
                Arrays.asList("shirt",
                        "oil",
                        "bread",
                        "butter",
                        "soap")
        );
        List<String> actual = categoriesHandler.getAllPurchasesTitleList();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getMaxCategory() {

        Category expected = new Category(CAR);
        expected.setSum(MAX_SUM);
        Category actual = categoriesHandler.getMaxCategory();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getSum(), actual.getSum());
    }
    @Test
    void getMaxCategory_notEvergreen() {
        Category expected = new Category(CAR);
        expected.setSum(1000);

        Category actual = categoriesHandler.getMaxCategory();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getSum(), actual.getSum());
    }

    @Test
    void getMaxYearCategory() {
        Category expected = new Category(FOOD);
        expected.setSum(MAX_YEAR_SUM);

        Category actual = categoriesHandler.getMaxYearCategory(YEAR);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getSum(), actual.getSum());
    }

    @Test
    void getMaxYearCategory_notEvergreen() {
        Category expected = new Category(CLOTHES);
        expected.setSum(MAX_YEAR_SUM);

        Category actual = categoriesHandler.getMaxYearCategory(YEAR);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getSum(), actual.getSum());
    }

    @Test
    void getMaxMonthCategory() {
        Category expected = new Category(CLOTHES);
        expected.setSum(MAX_MONTH_SUM);

        Category actual = categoriesHandler.getMaxMonthCategory(MONTH);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getSum(), actual.getSum());
    }

    @Test
    void getMaxMonthCategory_notEvergreen() {
        Category expected = new Category(HOME);
        expected.setSum(MAX_MONTH_SUM);

        Category actual = categoriesHandler.getMaxMonthCategory(MONTH);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getSum(), actual.getSum());
    }

    @Test
    void getMaxDayCategory() {
        Category expected = new Category(HOME);
        expected.setSum(MAX_DAY_SUM);

        Category actual = categoriesHandler.getMaxDayCategory(DAY);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getSum(), actual.getSum());
    }

    @Test
    void getMaxDayCategory_notEvergreen() {
        Category expected = new Category(CLOTHES);
        expected.setSum(MAX_DAY_SUM);

        Category actual = categoriesHandler.getMaxDayCategory(DAY);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getSum(), actual.getSum());
    }

    private HashSet<Category> getCategories() {
        HashSet<Category> set = new HashSet<>();

        Category food = new Category(FOOD);
        Purchase bread = new Purchase("bread", "2022.07.08", 200);
        Purchase butter  =new Purchase("butter", "2022.01.12", 300);
        food.addPurchaseAndSetCategorySum(bread);
        food.addPurchaseAndSetCategorySum(butter);

        Category clothes = new Category(CLOTHES);
        Purchase shirt = new Purchase("shirt", "2023.02.28", MAX_MONTH_SUM);
        clothes.addPurchaseAndSetCategorySum(shirt);

        Category home = new Category(HOME);
        Purchase soap =new Purchase("soap", "2021.11.11", MAX_DAY_SUM);
        home.addPurchaseAndSetCategorySum(soap);

        Category car = new Category(CAR);
        Purchase oil = new Purchase("oil", "2020.09.29", MAX_SUM);
        car.addPurchaseAndSetCategorySum(oil);

        set.add(food);
        set.add(clothes);
        set.add(home);
        set.add(car);

        return set;
    }
}