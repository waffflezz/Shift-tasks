package ru.shift;

import ru.shift.constants.DelimiterConstants;
import ru.shift.constants.TableConstants;

/**
 * Класс {@code MultiplicationTable} предназначен для генерации строкового
 * представления таблицы умножения заданного размера.
 * <p>
 * Таблица строится в текстовом виде с выравниванием ячеек и использованием
 * разделителей строк и столбцов.
 * </p>
 *
 * <p>
 * Ограничения:
 * <ul>
 *     <li>Размер таблицы должен быть в диапазоне от {@link TableConstants#LEFT_BOUND} до {@link TableConstants#RIGHT_BOUND} включительно.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Пример использования:
 * <pre>{@code
 * MultiplicationTable table = new MultiplicationTable(5);
 * System.out.println(table.getMultiplicationTable());
 * }</pre>
 * </p>
 */
public class MultiplicationTable {
    /**
     * Разделитель строки, в зависимости от системы
     */
    private static final String EOL = System.lineSeparator();
    /**
     * Множитель для первой строки таблицы
     */
    private static final int HEADER_MULTIPLIER = 1;

    private final int tableSize;
    private final int whiteSpaceLength;
    private final int cellLength;

    private final StringBuilder builder;
    private final String dividerRow;
    private final String firstCellFormat;
    private final String cellFormat;

    /**
     * Конструктор, создающий таблицу умножения заданного размера.
     *
     * @param tableSize размер таблицы (от 1 до 32 включительно)
     * @throws IllegalArgumentException если {@code tableSize} выходит за допустимые пределы
     */
    public MultiplicationTable(int tableSize) {
        Validator.validateTableSize(tableSize);

        this.tableSize = tableSize;
        this.whiteSpaceLength = String.valueOf(tableSize).length();
        this.cellLength = String.valueOf(tableSize * tableSize).length();

        this.dividerRow = generateDividerRow();

        this.firstCellFormat = "%" + (whiteSpaceLength > 1 ? whiteSpaceLength + "d" : "d");
        this.cellFormat = "%" + cellLength + "d";

        this.builder = initBuilder();
        fillTable();
    }

    /**
     * Возвращает сгенерированную таблицу умножения в виде строки.
     *
     * @return строковое представление таблицы умножения
     */
    public String getMultiplicationTable() {
        return builder.toString();
    }

    /**
     * Инициализирует {@link StringBuilder} с заранее рассчитанной вместимостью
     *
     * @return инициализированный {@link StringBuilder}
     */
    private StringBuilder initBuilder() {
        int allRowsLength = (whiteSpaceLength
                + cellLength * tableSize
                + tableSize
                + dividerRow.length()
                + EOL.length() * 2)
                * tableSize;

        return new StringBuilder(allRowsLength);
    }

    /**
     * Заполняет {@link StringBuilder} содержимым таблицы умножения.
     */
    private void fillTable() {
        builder.repeat(' ', whiteSpaceLength);
        fillRowWithoutFirstCol(HEADER_MULTIPLIER);

        for (int i = 1; i < tableSize + 1; i++) {
            fillRow(i);
        }
    }

    /**
     * Заполняет одну строку таблицы, включая первый столбец.
     *
     * @param startNumber число, с которого начинается строка
     */
    private void fillRow(int startNumber) {
        builder.append(firstCellFormat.formatted(startNumber));
        fillRowWithoutFirstCol(startNumber);
    }

    /**
     * Заполняет строку таблицы без первого столбца (только значения произведений).
     *
     * @param startNumber число, используемое для вычисления произведений
     */
    private void fillRowWithoutFirstCol(int startNumber) {
        for (int i = 1; i < tableSize + 1; i++) {
            builder.append(DelimiterConstants.VERTICAL);
            builder.append(cellFormat.formatted(startNumber * i));
        }

        builder.append(dividerRow);
    }

    /**
     * Генерирует строку-разделитель между строками таблицы.
     *
     * @return строка-разделитель
     */
    private String generateDividerRow() {
        String cellUnderscore = DelimiterConstants.UNDERSCORE.repeat(cellLength);
        return EOL + DelimiterConstants.UNDERSCORE.repeat(whiteSpaceLength)
                + DelimiterConstants.CROSS
                + (cellUnderscore + DelimiterConstants.CROSS).repeat(tableSize - 1)
                + cellUnderscore
                + EOL;
    }
}
