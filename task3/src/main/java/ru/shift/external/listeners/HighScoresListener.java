package ru.shift.external.listeners;

import ru.shift.dto.HighScoresDto;

@FunctionalInterface
public interface HighScoresListener extends ExternalListener {
    void onHighScoresChanged(HighScoresDto highScores);
}
