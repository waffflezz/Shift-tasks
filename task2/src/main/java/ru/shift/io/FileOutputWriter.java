package ru.shift.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Реализация {@link OutputWriter}, выполняющая запись данных в файл.
 *
 * <p>Запись осуществляется с использованием {@link BufferedWriter}
 * и кодировки UTF-8.</p>
 */
@Slf4j
public class FileOutputWriter implements OutputWriter {
    private final BufferedWriter writer;

    /**
     * Создаёт объект для записи в файл.
     *
     * <p>Каждая операция записи сопровождается принудительным сбросом буфера
     * ({@code flush}), что гарантирует немедленную запись данных в файл.</p>
     *
     * @param fileName путь к файлу для записи
     * @throws FileNotFoundException если файл не может быть создан или открыт
     */
    public FileOutputWriter(String fileName) throws FileNotFoundException {
        this.writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(fileName),
                        StandardCharsets.UTF_8
                )
        );
    }

    @Override
    public void write(String data) throws IOException {
        writer.write(data);
        writer.flush();
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            log.warn("При попытке закрыть BufferedWriter произошла ошибка: {}", e.getMessage());
        }
    }
}
