package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.example.category.CategoriesHandler;
import org.example.category.Category;
import org.example.net.Config;
import org.example.net.Request;
import org.example.net.Response;
import org.example.util.Statistics;
import org.example.util.StatisticsImpl;
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

        CategoriesHandler categoriesHolder = new CategoriesHandler(new HashSet<>());
        List<String> products = new ArrayList<>();

        Statistics<HashSet<Category>> stats = new StatisticsImpl();
        File statsFile = new File(StatisticsImpl.STATISTICS_FILE);
        File tsvFile = new File(CATEGORIES_FILE_PATH);

        if (statsFile.exists()) {
            categoriesHolder = new CategoriesHandler(stats.loadStats(statsFile));
            products = categoriesHolder.getAllPurchasesList();

            System.out.println("Read file '" + StatisticsImpl.STATISTICS_FILE + "':");
            categoriesHolder.getCategories().forEach(System.out::println);

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
                            category.addPurchase(row[PURCHASE_INDEX]);
                        }
                    }
                }
                System.out.println("Read file '" + CATEGORIES_FILE_PATH + "':");
                categoriesHolder.getCategories().forEach(System.out::println);

                stats.saveStats(statsFile, categoriesHolder.getCategories());

            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(Config.PORT)) {
            System.out.println("Server started");
            String requestAsString;

            while (true) {

                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                     ) {

                    requestAsString = in.readLine();
                    System.out.println("Request: " + requestAsString);
                    Request request = Utils.mapper.readValue(requestAsString, new TypeReference<Request>(){});

                    for (Category category : categoriesHolder.getCategories()) {

                        if (!products.contains(request.getTitle())) {
                            Category defaultCat =
                                    categoriesHolder.getCategoryByTitle(Category.DEFAULT_CATEGORY_TITLE);
                            if (defaultCat == null) {
                                defaultCat = new Category(Category.DEFAULT_CATEGORY_TITLE);
                                categoriesHolder.addCategory(defaultCat);
                            }
                            defaultCat.addPurchase(request.getTitle());
                            defaultCat.setSum(defaultCat.getSum() + request.getSum());
                            break;
                        }

                        for (String purchase : category.getPurchases()) {
                            if (purchase.equals(request.getTitle())){
                                category.setSum(category.getSum() + request.getSum());
                            }
                        }
                    }
                    categoriesHolder.getCategories().forEach(System.out::println);
                    stats.saveStats(statsFile, categoriesHolder.getCategories());

                    Category maxSumCategory = categoriesHolder.getMaxSumCategory();
                    Response response = new Response(maxSumCategory.getTitle(), maxSumCategory.getSum());
                    out.println(Utils.mapper.writeValueAsString(response));

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
