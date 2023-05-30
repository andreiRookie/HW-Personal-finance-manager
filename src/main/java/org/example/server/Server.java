package org.example.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.example.Utils;
import org.example.categories.CategoriesHandler;
import org.example.categories.Category;
import org.example.client.Request;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final int PURCHASE_INDEX = 0;
    private static final int CATEGORY_INDEX = 1;

    public static void main(String[] args) {

        String requestAsString;

        CategoriesHandler categoriesHolder = new CategoriesHandler();

        List<String> products = new ArrayList<>();

        // TO SEPARATE CLASS
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator('\t')
                .build();

        File tsvFile = new File(Utils.CATEGORIES_FILE_PATH);

        if (tsvFile.exists()) {
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

                categoriesHolder.getCategories().forEach(System.out::println);

            }catch (IOException | CsvException e) {
                e.printStackTrace();
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(Config.PORT)) {
            System.out.println("Server started");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                     ) {

                    requestAsString = in.readLine();
                    Request request = Utils.mapper.readValue(requestAsString, new TypeReference<Request>(){});
                    System.out.println("Request: " + request.toString());

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

                    Category maxCategory = categoriesHolder.getMaxSumCategory();
                    Response response = new Response(maxCategory.getTitle(), maxCategory.getSum());

                    Utils.mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
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
