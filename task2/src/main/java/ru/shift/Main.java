package ru.shift;

import picocli.CommandLine;
import ru.shift.cli.ShapeCommand;

/**
 * Точка входа в приложение.
 *
 * <p>После регистрации управление передаётся {@link ShapeCommand},
 * которая обрабатывает аргументы командной строки и выполняет основную логику приложения.</p>
 */
public class Main {
    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new ShapeCommand()).execute(args);
        System.exit(exitCode);
    }
}
