package ru.shift.cli;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import ru.shift.exceptions.UnknownShapeTypeException;
import ru.shift.exceptions.WrongParamCountException;
import ru.shift.factories.ShapeFactoryProvider;
import ru.shift.factories.reading.ReadingShapeFactoryProvider;
import ru.shift.format.string.StringFormatterRegistry;
import ru.shift.io.ConsoleOutputWriter;
import ru.shift.io.FileInputReader;
import ru.shift.io.FileOutputWriter;
import ru.shift.shapes.Shape;
import ru.shift.utils.FileUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Класс команды командной строки для чтения описания геометрической фигуры из файла,
 * создания соответствующего объекта и вывода его характеристик.
 *
 * <p>Команда:
 * <ul>
 *     <li>читает тип фигуры из входного файла</li>
 *     <li>определяет подходящую фабрику через {@link ShapeFactoryProvider}</li>
 *     <li>создаёт объект фигуры</li>
 *     <li>Достаёт нужный форматер через {@link StringFormatterRegistry}</li>
 *     <li>форматирует результат через детей {@link ru.shift.format.string.StringFormatter}</li>
 *     <li>выводит результат в файл или консоль</li>
 * </ul>
 *
 * <p>Поддерживаемые способы вывода:
 * <ul>
 *     <li>в файл через {@code -o}/{@code --output}</li>
 *     <li>в консоль через {@code -co}/{@code --console-output}</li>
 * </ul>
 *
 * <p>Используется как точка входа для CLI-утилиты на базе Picocli.</p>
 */
@Slf4j
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
                description = "Путь до выходного файла"
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
        try {
            var readingFactoryProvider = new ReadingShapeFactoryProvider();

            FileUtil.createDirectoryIfNotExists(outputOptions.outputFile);

            try (var reader = new FileInputReader(inputFile);
                 var writer = outputOptions.consoleOutput
                         ? new ConsoleOutputWriter()
                         : new FileOutputWriter(outputOptions.outputFile)) {

                log.info("Чтение входного файла: {}, и создание фигуры по данным из него", inputFile);
                String shapeText = reader.readLine(readingFactoryProvider.getMaxShapeTypeLength());

                var factory = readingFactoryProvider.getFactory(shapeText)
                        .orElseThrow(() -> new UnknownShapeTypeException(shapeText));
                Shape shape = factory.create(reader);

                log.info("Создана фигура: {}", factory.getShapeType());
                writer.write(
                        new StringFormatterRegistry()
                                .getFormatter(shape)
                                .format(shape, factory.getShapeType())
                );
            }
        } catch (FileNotFoundException e) {
            log.error("При открытии файла произошла ошибка: {}", e.getMessage());
            log.debug("Подробная ошибка", e);
            System.exit(1);
        } catch (IOException e) {
            log.error("При работе с файлами произошла ошибка: {}", e.getMessage());
            log.debug("Подробная ошибка", e);
            System.exit(2);
        } catch (WrongParamCountException e) {
            log.error("Неверное количество аргументов для фигуры. Ошибка: {}", e.getMessage());
            log.debug("Подробная ошибка", e);
            System.exit(3);
        } catch (UnknownShapeTypeException e) {
            log.error(e.getMessage());
            log.debug("Подробная ошибка", e);
            System.exit(4);
        } catch (Exception e) {
            log.error("Произошла непредвиденная ошибка: {}", e.getMessage());
            log.debug("Подробная ошибка:", e);
            System.exit(5);
        }
        log.info("Успешное завершение программы");
    }
}

