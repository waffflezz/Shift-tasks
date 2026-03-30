package ru.shift.io;

import java.io.IOException;

/**
 * Интерфейс для абстракции записи данных.
 *
 * <p>Позволяет реализовывать различные способы вывода,
 * например: в файл или консоль.</p>
 *
 * <p>Расширяет {@link AutoCloseable}, поэтому реализация должна корректно
 * освобождать ресурсы.</p>
 */
public interface OutputWriter extends AutoCloseable {
    /**
     * Записывает переданную строку данных.
     *
     * @param data данные для записи
     * @throws IOException если произошла ошибка ввода-вывода
     */
    void write(String data) throws IOException;
}
