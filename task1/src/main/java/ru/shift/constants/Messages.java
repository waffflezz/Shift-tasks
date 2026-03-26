package ru.shift.constants;

/**
 * Класс {@code Messages}, содержащий текстовые константы,
 * используемые в пользовательском интерфейсе и при обработке ошибок.
 */
public class Messages {
    public static final String NOT_A_NUMBER = "Вы ввели не число, попробуйте ещё раз!";
    public static final String UNEXPECTED_ERROR = "Произошла непредвиденная ошибка. Попробуйте ещё раз";
    public static final String INPUT_TABLE_SIZE = """
                Введите число от %d до %d, чтобы посчитать таблицу умножения введённой размерности
                Чтобы остановить выполнение программы, введите %d
                """;
    public static final String INVALID_TABLE_SIZE = """
                Некорректный размер таблицы: %d
                Допустимое значение: от %d до %d включительно
                """;
}
