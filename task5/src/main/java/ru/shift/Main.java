package ru.shift;

import picocli.CommandLine;
import ru.shift.cli.ProducerConsumerCommand;

/**
 * Точка входа в приложение.
 */
public class Main {
    /**
     * Запускает обработку аргументов командной строки и завершает процесс с полученным кодом выхода.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        new CommandLine(new ProducerConsumerCommand()).execute(args);
    }
}
