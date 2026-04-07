package ru.shift.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Реализация {@link InputReader}, выполняющая чтение данных из файла.
 *
 * <p>Чтение осуществляется посимвольно с использованием {@link BufferedReader}
 * и кодировки UTF-8.</p>
 *
 * <p>Метод {@link #readLine} читает строку до символа перевода строки
 * или до достижения максимальной длины.</p>
 */
@Slf4j
public class FileInputReader implements InputReader {
    private final BufferedReader reader;

    /**
     * Создаёт объект для чтения из файла с стандартным ограничением по длине.
     *
     * @param filePath путь к входному файлу
     * @throws FileNotFoundException если файл не найден или недоступен
     */
    public FileInputReader(String filePath) throws FileNotFoundException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath),
                        StandardCharsets.UTF_8
                )
        );
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine().trim();
    }

    @Override
    public void close() throws Exception {
        try {
            reader.close();
        } catch (IOException e) {
            log.warn("При попытке закрыть BufferedReader произошла ошибка: {}", e.getMessage());
        }
    }
}
