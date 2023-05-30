package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.format.DateTimeFormatter;

public class Utils {
    public static final String CATEGORIES_FILE_PATH = "categories.tsv";
    public static final ObjectMapper mapper = new ObjectMapper();
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
}
