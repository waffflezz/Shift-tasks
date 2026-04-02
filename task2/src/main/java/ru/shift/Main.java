package ru.shift;

import picocli.CommandLine;
import ru.shift.cli.ShapeCommand;

/**
 * Точка входа в приложение.
 *
 * <p>Перед запуском CLI-команды выполняет глобальную регистрацию:
 * форматтеров строкового представления фигур и фабрик для их создания.</p>
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
