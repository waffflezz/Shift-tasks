package ru.shift.cli;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

@Command(
        name = "Фигуры",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Утилита для определения фигуры с параметрами из входного файла, " +
                "а также вывод различных характеристик фигуры"
)
public class ShapeCommand implements Runnable {
    @Option(
            names = {"-f", "--file"},
            required = true,
            description = "Путь до входящего файла"
    )
    private String inputFile;

    @ArgGroup(multiplicity = "1")
    private OutputOptions outputOptions;

    static class OutputOptions {
        @Option(
                names = {"-o", "--output"},
                description = "Путь до входного файла"
        )
        private String outputFile;

        @Option(
                names = {"-co", "--console-output"},
                description = "Вывод результата в консоль"
        )
        private boolean consoleOutput;
    }

    @Override
    public void run() {
        System.out.println(inputFile);
    }
}
