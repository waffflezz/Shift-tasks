package ru.shift;

public class MultiplicationTableGenerator {
    private final String EOL = System.lineSeparator();

    private final int tableSize;
    private final int whiteSpaceLength;
    private final int cellLength;

    private final StringBuilder mainBuilder;
    private final String dividerRow;
    private final String firstCellFormat;
    private final String cellFormat;


    public MultiplicationTableGenerator(int tableSize) {
        if (tableSize < 1 || tableSize > 32) {
            throw new IllegalArgumentException("Аргумент tableSize должен быть 1 <= tableSize <= 32");
        }

        this.tableSize = tableSize;
        this.whiteSpaceLength = String.valueOf(tableSize).length();
        this.cellLength = String.valueOf(tableSize * tableSize).length();

        this.dividerRow = generateDividerRow();

        int allRowsLength = (whiteSpaceLength
                + cellLength * tableSize
                + tableSize // Кол-во вертикальных разделителей "|"
                + dividerRow.length()
                + EOL.length() * 2) // EOL, конец файла для одной линии + разделительная линия
                * tableSize;


        this.firstCellFormat = "%" + (whiteSpaceLength > 1 ? whiteSpaceLength + "d" : "d");
        this.cellFormat = "%" + cellLength + "d";

        this.mainBuilder = new StringBuilder(allRowsLength);
        fillTable();
    }

    public String getMultiplicationTable() {
        return mainBuilder.toString();
    }

    private void fillTable() {
        mainBuilder.append(" ".repeat(whiteSpaceLength));
        fillRowWithoutFirstCol(1);

        for (int i = 1; i < tableSize + 1; i++) {
            fillRow(i);
        }
    }

    private void fillRow(int startNumber) {
        mainBuilder.append(String.format(firstCellFormat, startNumber));
        fillRowWithoutFirstCol(startNumber);
    }

    private void fillRowWithoutFirstCol(int startNumber) {
        for (int i = 1; i < tableSize + 1; i++) {
            mainBuilder.append(DelimiterConstants.VERTICAL);
            mainBuilder.append(String.format(cellFormat, startNumber * i));
        }

        mainBuilder.append(EOL).append(dividerRow).append(EOL);
    }

    private String generateDividerRow() {
        String cellUnderscore = DelimiterConstants.UNDERSCORE.repeat(cellLength);
        return DelimiterConstants.UNDERSCORE.repeat(whiteSpaceLength)
                + DelimiterConstants.CROSS
                + (cellUnderscore + DelimiterConstants.CROSS).repeat(tableSize - 1)
                + cellUnderscore;
    }
}
