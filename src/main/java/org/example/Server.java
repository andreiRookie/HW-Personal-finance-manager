package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.example.category.CategoriesHandler;
import org.example.category.Category;
import org.example.category.Purchase;
import org.example.net.Config;
import org.example.net.Request;
import org.example.net.Response;
import org.example.stats.*;
import org.example.util.Utils;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private static final int PURCHASE_INDEX = 0;
    private static final int CATEGORY_INDEX = 1;
    private static final String CATEGORIES_FILE_PATH = "categories.tsv";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CategoriesHandler categoriesHolder = new CategoriesHandler(new HashSet<>());
        List<String> products = new ArrayList<>();

        Statistics<StatisticsObject> stats = new StatisticsImpl();
        File statsFile = new File(StatisticsImpl.STATISTICS_FILE);
        File tsvFile = new File(CATEGORIES_FILE_PATH);

        // Categories & products from statistics file
        if (statsFile.exists()) {
            StatisticsObject statsObj = stats.loadStats(statsFile);
            categoriesHolder = new CategoriesHandler(statsObj.getCategories());
            products = categoriesHolder.getAllPurchasesTitleList();

            System.out.println("Read file '" + StatisticsImpl.STATISTICS_FILE + "':");
            categoriesHolder.getCategories().forEach(System.out::println);

        // Categories & products from categories.tsv file
        } else if (tsvFile.exists()) {

            CSVParser csvParser = new CSVParserBuilder()
                    .withSeparator('\t')
                    .build();

            try( CSVReader csvReader = new CSVReaderBuilder(new FileReader(tsvFile))
                    .withCSVParser(csvParser)
                    .build()) {

                List<String[]> rows = csvReader.readAll();
                for (String[] row : rows) {
                    products.add(row[PURCHASE_INDEX]);
                    categoriesHolder.addCategory(new Category(row[CATEGORY_INDEX]));
                }
                for (Category category : categoriesHolder.getCategories()) {
                    for (String[] row : rows) {
                        if (row[CATEGORY_INDEX].equals(category.getTitle())) {
                            category.addPurchase(new Purchase(row[PURCHASE_INDEX], "", 0));
                        }
                    }
                }
                System.out.println("Read file '" + CATEGORIES_FILE_PATH + "':");
                categoriesHolder.getCategories().forEach(System.out::println);

            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(Config.PORT)) {
            System.out.println("Server started");
            String requestAsString;

            while (true) {
                boolean shouldAddPurchase = false;

                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                ) {

                    requestAsString = in.readLine();
                    System.out.println("Request: " + requestAsString);
                    Request request = Utils.mapper.readValue(requestAsString, new TypeReference<Request>(){});
                    Purchase newPurchase = new Purchase(request.getTitle(),request.getDate(), request.getSum());

                    for (Category category : categoriesHolder.getCategories()) {

                        // default category
                        if (!products.contains(newPurchase.getTitle())) {
                            Category defaultCat =
                                    categoriesHolder.getCategoryByTitle(Category.DEFAULT_CATEGORY_TITLE);
                            if (defaultCat == null) {
                                defaultCat = new Category(Category.DEFAULT_CATEGORY_TITLE);
                                categoriesHolder.addCategory(defaultCat);
                            }
                            defaultCat.addPurchaseAndSetCategorySum(newPurchase);
                            break;
                        }

                        for (Purchase purchase : category.getPurchases()) {
                            if (purchase.getTitle().equals(request.getTitle())) {
                                shouldAddPurchase = true;
                                break;
                            }
                        }
                        if (shouldAddPurchase) {
                            category.addPurchaseAndSetCategorySum(newPurchase);
                            shouldAddPurchase = false;
                        }
                    }
                    System.out.println("Current categories:");
                    categoriesHolder.getCategories().forEach(System.out::println);

                    // maxCategory server response
                    Category maxCat = categoriesHolder.getMaxCategory();
                    Response response = new Response(maxCat.getTitle(), maxCat.getSum());
                    out.println(Utils.mapper.writeValueAsString(response));

                    // Stats
                    try {

                        StatisticsCategory maxStatCat = new StatisticsCategory(maxCat.getTitle(), maxCat.getSum());

                        System.out.println("Enter year 'yyyy' to get year stats");
                        String year = scanner.nextLine();
                        Category maxYearCat = categoriesHolder.getMaxYearCategory(year);
                        StatisticsCategory maxYearStatCat = new StatisticsCategory(maxYearCat.getTitle(), maxYearCat.getSum());

                        System.out.println("Enter month 'mm' to get month stats");
                        String month = scanner.nextLine();
                        Category maxMonthCat = categoriesHolder.getMaxMonthCategory(month);
                        StatisticsCategory maxMonthStatCat = new StatisticsCategory(maxMonthCat.getTitle(), maxMonthCat.getSum());

                        System.out.println("Enter day 'dd' to get day stats");
                        String day = scanner.nextLine();
                        Category maxDayCat = categoriesHolder.getMaxDayCategory(day);
                        StatisticsCategory maxDayStatCat = new StatisticsCategory(maxDayCat.getTitle(), maxDayCat.getSum());

                        StatisticsObject statsObj = new StatisticsObject(
                                categoriesHolder.getCategories(),
                                maxStatCat,
                                maxYearStatCat,
                                maxMonthStatCat,
                                maxDayStatCat
                        );
                        stats.saveStats(statsFile, statsObj);
                        System.out.println("Stats adding succeeded");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        System.out.println("Stats adding failed");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.out.println("Cannot start server");
            e.printStackTrace();
        }
    }
}
