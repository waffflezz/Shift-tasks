package ru.shift.cli;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import ru.shift.exceptions.UnknownShapeTypeException;
import ru.shift.exceptions.WrongParamCountException;
import ru.shift.factories.FactoryRegistry;
import ru.shift.factories.ShapeFactory;
import ru.shift.format.FormatterFactory;
import ru.shift.format.string.StringFormatterRegistry;
import ru.shift.io.ConsoleOutputWriter;
import ru.shift.io.FileInputReader;
import ru.shift.io.FileOutputWriter;
import ru.shift.io.OutputWriter;
import ru.shift.shapes.Shape;
import ru.shift.shapes.types.Mapper;
import ru.shift.shapes.types.ShapeType;
import ru.shift.utils.FileUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

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
        try {
            FileUtil.createDirectoryIfNotExists(outputOptions.outputFile);

            OutputWriter writer = outputOptions.consoleOutput
                    ? new ConsoleOutputWriter()
                    : new FileOutputWriter(outputOptions.outputFile);

            try (var reader = new FileInputReader(inputFile);
                 writer) {
                FormatterFactory<String> stringFormatterFactory = new StringFormatterRegistry();

                log.info("Чтение входного файла: {}, и создание фигуры по данным из него", inputFile);
                String shapeText = reader.readLine(ShapeType.computeMaxLengthFigureType());
                ShapeType shapeType = Mapper.fromStringToShapeType(shapeText);

                ShapeFactory<?> factory = FactoryRegistry.getFactory(shapeType);

                int maxStringLength = factory.getParamsNeedCount() * String.valueOf(Double.MAX_VALUE).length();
                String[] params = reader.readLine(maxStringLength).split(" ");

                Shape shape = factory.create(params);
                log.info("Создана фигура с типом: {}", shape.getShapeType());

                writer.write(stringFormatterFactory.getFormatter(shape).format(shape));
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

