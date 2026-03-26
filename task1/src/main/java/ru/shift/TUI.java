package ru.shift;

import ru.shift.constants.Messages;
import ru.shift.constants.TableConstants;
import ru.shift.exceptions.InvalidTableSizeException;

import java.util.Scanner;

/**
 * Класс {@code TUI} реализует простой текстовый пользовательский интерфейс
 * для генерации таблицы умножения заданной размерности
 */
public class TUI {
    private static final int EXIT_INPUT = 0;

    /**
     * Запускает цикл обработки пользовательского ввода.
     * <p>
     * В бесконечном цикле запрашивает у пользователя ввод числа:
     * <ul>
     *     <li>Если введено {@link TUI#EXIT_INPUT} — завершает выполнение</li>
     *     <li>Если введено число от {@link TableConstants#LEFT_BOUND} до {@link TableConstants#RIGHT_BOUND} — генерирует и выводит таблицу умножения</li>
     *     <li>Если введён некорректный ввод — выводит сообщение об ошибке</li>
     * </ul>
     * </p>
     */
    public static void handle() {
        final String inputMessage = Messages.INPUT_TABLE_SIZE.formatted(
                TableConstants.LEFT_BOUND,
                TableConstants.RIGHT_BOUND,
                EXIT_INPUT);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(inputMessage);

            if (!scanner.hasNextInt()) {
                System.out.println(Messages.NOT_A_NUMBER);
                scanner.next();
                continue;
            }

            int tableSize = scanner.nextInt();

            if (tableSize == EXIT_INPUT) {
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
