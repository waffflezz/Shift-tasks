package ru.shift;

import picocli.CommandLine;
import ru.shift.cli.MultiThreadCommand;

/**
 * Главная точка входа в приложение.
 */
public final class Main {
    /**
     * Запускает обработчик командной строки и завершает процесс с возвращённым кодом.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new MultiThreadCommand()).execute(args);
        System.exit(exitCode);
    }
}
