package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static final String CATEGORIES_FILE_PATH = "categories.tsv";
    public static final ObjectMapper mapper = new ObjectMapper();
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
}
