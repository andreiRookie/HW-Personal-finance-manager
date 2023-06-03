package org.example.stats;

import java.io.File;
public interface Statistics<T> {
    void saveStats(File file, T type);
    T loadStats(File file);
}
