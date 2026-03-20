package ru.shift;

public class MultiplicationTable {
    private final String EOL = System.lineSeparator();

    private final int tableSize;
    private final int whiteSpaceLength;
    private final int cellLength;

    private final StringBuilder builder;
    private final String dividerRow;
    private final String firstCellFormat;
    private final String cellFormat;


    public MultiplicationTable(int tableSize) {
        if (tableSize < 1 || tableSize > 32) {
            throw new IllegalArgumentException("Аргумент tableSize должен быть 1 <= tableSize <= 32");
        }

        this.tableSize = tableSize;
        this.whiteSpaceLength = String.valueOf(tableSize).length();
        this.cellLength = String.valueOf(tableSize * tableSize).length();

        this.dividerRow = generateDividerRow();

        this.firstCellFormat = "%" + (whiteSpaceLength > 1 ? whiteSpaceLength + "d" : "d");
        this.cellFormat = "%" + cellLength + "d";

        this.builder = initBuilder();
        fillTable();
    }

    public String getMultiplicationTable() {
        return builder.toString();
    }

    private StringBuilder initBuilder() {
        int allRowsLength = (whiteSpaceLength
                + cellLength * tableSize
                + tableSize // Кол-во вертикальных разделителей "|"
                + dividerRow.length()
                + EOL.length() * 2) // EOL, конец строки, для строки с строчками + раздилительной строки
                * tableSize;

        return new StringBuilder(allRowsLength);
    }

    private void fillTable() {
        builder.append(" ".repeat(whiteSpaceLength));
        fillRowWithoutFirstCol(1);

        for (int i = 1; i < tableSize + 1; i++) {
            fillRow(i);
        }
    }

    private void fillRow(int startNumber) {
        builder.append(firstCellFormat.formatted(startNumber));
        fillRowWithoutFirstCol(startNumber);
    }

    private void fillRowWithoutFirstCol(int startNumber) {
        for (int i = 1; i < tableSize + 1; i++) {
            builder.append(DelimiterConstants.VERTICAL);
            builder.append(cellFormat.formatted(startNumber * i));
        }

        builder.append(dividerRow);
    }

    private String generateDividerRow() {
        String cellUnderscore = DelimiterConstants.UNDERSCORE.repeat(cellLength);
        return EOL + DelimiterConstants.UNDERSCORE.repeat(whiteSpaceLength)
                + DelimiterConstants.CROSS
                + (cellUnderscore + DelimiterConstants.CROSS).repeat(tableSize - 1)
                + cellUnderscore
                + EOL;
    }
}
