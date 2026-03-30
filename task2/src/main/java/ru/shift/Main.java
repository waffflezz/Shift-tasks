package ru.shift;

import picocli.CommandLine;
import ru.shift.cli.ShapeCommand;
import ru.shift.factories.CircleFactory;
import ru.shift.factories.FactoryRegistry;
import ru.shift.factories.RectangleFactory;
import ru.shift.factories.TriangleFactory;
import ru.shift.format.string.CircleStringFormatter;
import ru.shift.format.string.RectangleStringFormatter;
import ru.shift.format.string.StringFormatterRegistry;
import ru.shift.format.string.TriangleStringFormatter;
import ru.shift.shapes.Circle;
import ru.shift.shapes.Rectangle;
import ru.shift.shapes.Triangle;

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
        globalRegistry();

        int exitCode = new CommandLine(new ShapeCommand()).execute(args);
        System.exit(exitCode);
    }

    /**
     * Выполняет глобальную регистрацию форматтеров и фабрик фигур.
     *
     * <p>Регистрируются:
     * <ul>
     *     <li>форматтеры</li>
     *     <li>фабрики</li>
     * </ul>
     *
     * <p>Регистрация выполняется один раз перед запуском приложения.</p>
     */
    private static void globalRegistry() {
        StringFormatterRegistry.registerFormatter(Circle.class, new CircleStringFormatter());
        StringFormatterRegistry.registerFormatter(Rectangle.class, new RectangleStringFormatter());
        StringFormatterRegistry.registerFormatter(Triangle.class, new TriangleStringFormatter());

        FactoryRegistry.registerFactory(new CircleFactory());
        FactoryRegistry.registerFactory(new RectangleFactory());
        FactoryRegistry.registerFactory(new TriangleFactory());
    }
}
