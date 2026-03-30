package ru.shift.io;

import java.io.IOException;

/**
 * Интерфейс для абстракции чтения входных данных.
 *
 * <p>Позволяет реализовывать различные источники ввода,
 * например: файл или консоль.</p>
 *
 * <p>Расширяет {@link AutoCloseable}, поэтому реализация должна корректно
 * освобождать ресурсы.</p>
 */
public interface InputReader extends AutoCloseable {
    /**
     * Читает одну строку из источника ввода с ограничением по длине.
     *
     * <p>Если длина строки превышает {@code maxLength},
     * поведение определяется реализацией.</p>
     *
     * @param maxLength максимальная допустимая длина строки
     * @return прочитанная строка
     * @throws IOException если произошла ошибка ввода-вывода
     */
    String readLine(int maxLength) throws IOException;
}
