package ru.shift;

import java.util.Scanner;

/**
 * Класс {@code TUI} реализует простой текстовый пользовательский интерфейс
 * для генерации таблицы умножения заданной размерности
 */
public class TUI {
    /**
     * Запускает цикл обработки пользовательского ввода.
     * <p>
     * В бесконечном цикле запрашивает у пользователя ввод числа:
     * <ul>
     *     <li>Если введено 0 — завершает выполнение</li>
     *     <li>Если введено число от 1 до 32 — генерирует и выводит таблицу умножения</li>
     *     <li>Если введён некорректный ввод — выводит сообщение об ошибке</li>
     * </ul>
     * </p>
     */
    public static void handle() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите число от 1 до 32, " +
                    "чтобы посчитать таблицу умножения, введённой размерности\n" +
                    "Чтобы остановить выполнение программы, введите 0");

            if (!scanner.hasNextInt()) {
                System.out.println("Вы ввели не число, попробуйте ещё раз!");
                scanner.next();
                continue;
            }

            int tableSize = scanner.nextInt();

            if (tableSize == 0) {
                break;
            }

            try {
                MultiplicationTable table = new MultiplicationTable(tableSize);
                System.out.println(table.getMultiplicationTable());
            } catch (IllegalArgumentException e) {
                System.out.println("Число должно быть больше 1 и меньше 32 включительно!");
            }
        }
    }
}
