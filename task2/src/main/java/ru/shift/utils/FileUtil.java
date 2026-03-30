package ru.shift.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static void createDirectoryIfNotExists(String filePath) throws IOException {
        if (filePath == null) {
            return;
        }

        Path path = Path.of(filePath);
        Path parent = path.getParent();

        if (parent != null && Files.notExists(parent)) {
            Files.createDirectories(parent);
        }
    }
}
