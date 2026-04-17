package ru.shift.external.listeners;

import ru.shift.dto.HighScoresDto;

/**
 * Получает обновления таблицы рекордов.
 */
@FunctionalInterface
public interface HighScoresListener extends ExternalListener {
    /**
     * Обрабатывает обновлённые рекорды.
     *
     * @param highScores обновлённые рекорды
     */
    void onHighScoresChanged(HighScoresDto highScores);
}
