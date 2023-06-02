package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.category.Category;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public class StatisticsImpl implements Statistics<HashSet<Category>> {
    public static final String STATISTICS_FILE = "data.bin";

    public StatisticsImpl() {}

    @Override
    public void saveStats(File file, HashSet<Category> categories) {
        if (categories == null) {
            throw new IllegalArgumentException("categories must not be null");
        }
        try (FileWriter writer  = new FileWriter(file)){
            writer.write(Utils.mapper.writeValueAsString(categories));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashSet<Category> loadStats(File file) {
        HashSet<Category> result = new HashSet<>();
        try (Scanner scanner = new Scanner(file)) {
            String input = scanner.nextLine();
            result = Utils.mapper.readValue(input, new TypeReference<HashSet<Category>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
