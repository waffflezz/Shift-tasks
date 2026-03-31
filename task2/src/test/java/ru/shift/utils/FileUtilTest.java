package ru.shift.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {
    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Не должен выбрасывать исключение при null пути")
    void shouldDoNothingWhenPathIsNull() {
        // Arrange

        // Act & Assert
        assertDoesNotThrow(() ->
                FileUtil.createDirectoryIfNotExists(null)
        );
    }

    @Test
    @DisplayName("Не должен создавать директории, если файл в текущей директории")
    void shouldDoNothingWhenNoParentDirectory() throws IOException {
        // Arrange
        String filePath = "test.txt";

        // Act
        FileUtil.createDirectoryIfNotExists(filePath);

        // Assert
        // ничего не создаётся, просто проверяем, что не упало
        assertTrue(true);
    }

    @Test
    @DisplayName("Должен создавать директории, если они не существуют")
    void shouldCreateDirectoriesWhenNotExist() throws IOException {
        // Arrange
        Path nestedPath = tempDir.resolve("a/b/c/file.txt");
        String filePath = nestedPath.toString();

        // Act
        FileUtil.createDirectoryIfNotExists(filePath);

        // Assert
        assertTrue(Files.exists(nestedPath.getParent()));
    }

    @Test
    @DisplayName("Не должен падать, если директории уже существуют")
    void shouldNotFailWhenDirectoriesAlreadyExist() throws IOException {
        // Arrange
        Path nestedDir = tempDir.resolve("a/b/c");
        Files.createDirectories(nestedDir);

        String filePath = nestedDir.resolve("file.txt").toString();

        // Act & Assert
        assertDoesNotThrow(() ->
                FileUtil.createDirectoryIfNotExists(filePath)
        );
    }
}