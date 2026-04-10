package ru.shift.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Утилитный класс для работы с файловой системой.
 */
@Slf4j
public class FileUtil {
    /**
     * Создаёт родительские директории для указанного пути к файлу,
     * если они ещё не существуют.
     *
     * <p>Метод не создаёт сам файл — только директории, в которых он должен находиться.</p>
     *
     * <p>Если переданный путь {@code null} или не содержит родительской директории
     * (например, файл в текущей директории), метод ничего не делает.</p>
     *
     * @param filePath путь к файлу, для которого необходимо создать директории
     * @throws IOException ошибка при создании директорий
     */
    public static void createDirectoryIfNotExists(String filePath) throws IOException {
        log.info("Попытка создать директории, если не созданы по пути: {}", filePath);
        if (filePath == null) {
            return;
        }

        Path path = Path.of(filePath);
        Path parent = path.getParent();

        if (parent != null && Files.notExists(parent)) {
            Files.createDirectories(parent);
            log.trace("Директории успешно созданы");
        }
    }
}
