package ru.shift.io;

/**
 * Реализация {@link OutputWriter}, выполняющая вывод данных в консоль.
 *
 * <p>Вывод осуществляется в стандартный поток вывода ({@link System#out}).</p>
 */
public class ConsoleOutputWriter implements OutputWriter {
    @Override
    public void write(String data) {
        System.out.println(data);
    }

    @Override
    public void close() {}
}
