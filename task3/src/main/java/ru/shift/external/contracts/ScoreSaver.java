package ru.shift.external.contracts;

/**
 * Сохраняет результат игрока.
 */
public interface ScoreSaver {
    /**
     * Сохраняет текущий результат под указанным именем игрока.
     *
     * @param playerName имя игрока для сохранения
     */
    void saveScore(String playerName);
}
