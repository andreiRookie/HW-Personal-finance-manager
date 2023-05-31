package org.example.util;

public interface JsonFormatter<T> {
    String toJsonString(T string);
    T fromJsonFormat(String string);
}

