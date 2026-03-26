package ru.shift;

import ru.shift.constants.TableConstants;
import ru.shift.exceptions.InvalidTableSizeException;

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
        final int exitInput = 0;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(Messages.INPUT_TABLE_SIZE.formatted(
                    TableConstants.LEFT_BOUND,
                    TableConstants.RIGHT_BOUND,
                    exitInput));

            if (!scanner.hasNextInt()) {
                System.out.println(Messages.NOT_A_NUMBER);
                scanner.next();
                continue;
            }

            int tableSize = scanner.nextInt();

            if (tableSize == exitInput) {
                break;
            }

            try {
                MultiplicationTable table = new MultiplicationTable(tableSize);
                System.out.println(table.getMultiplicationTable());
            } catch (InvalidTableSizeException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(Messages.UNEXPECTED_ERROR);
            }
        }
    }
}
