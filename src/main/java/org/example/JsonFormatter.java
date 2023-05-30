package org.example;

public interface JsonFormatter<T> {
    String toJsonString(T string);
    T fromJsonFormat(String string);
}

