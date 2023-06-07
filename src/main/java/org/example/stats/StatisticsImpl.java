package org.example.stats;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.util.Utils;

import java.io.*;
import java.util.Scanner;

public class StatisticsImpl implements Statistics<StatisticsObject> {
    public static final String STATISTICS_FILE = "data.bin";

    public StatisticsImpl() {}

    @Override
    public void saveStats(File file, StatisticsObject statObj) {
        if (statObj == null) {
            throw new IllegalArgumentException("StatisticsObject must not be null");
        }
        try (FileWriter writer  = new FileWriter(file)){
            writer.write(Utils.mapper.writeValueAsString(statObj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public StatisticsObject loadStats(File file) {
        StatisticsObject result = null;
        try (Scanner scanner = new Scanner(file)) {
            String input = scanner.nextLine();
            result = Utils.mapper.readValue(input, new TypeReference<StatisticsObject>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
