package ru.shift.external.score;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import ru.shift.GameLevel;
import ru.shift.dto.HighScoresDto;
import ru.shift.dto.ScoreDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class ScoreRepository {
    private static final ScoreDto DEFAULT_SCORE = new ScoreDto("Unknown", 999);
    private static final HighScoresDto DEFAULT_HIGH_SCORES = new HighScoresDto(
            DEFAULT_SCORE,
            DEFAULT_SCORE,
            DEFAULT_SCORE
    );

    private final Path scoresFilePath;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    public HighScoresDto load() {
        ensureScoresFileExists();

        try {
            return objectMapper.readValue(scoresFilePath.toFile(), HighScoresDto.class);
        } catch (IOException e) {
            write(DEFAULT_HIGH_SCORES);
            return DEFAULT_HIGH_SCORES;
        }
    }

    public HighScoresDto saveIfBetter(GameLevel gameLevel, ScoreDto score) {
        HighScoresDto currentHighScores = load();
        ScoreDto currentScore = getScoreForLevel(currentHighScores, gameLevel);

        if (!isBetter(score, currentScore)) {
            return currentHighScores;
        }

        HighScoresDto updatedHighScores = switch (gameLevel) {
            case NOVICE -> new HighScoresDto(score, currentHighScores.mediumRecord(), currentHighScores.expertRecord());
            case MEDIUM -> new HighScoresDto(currentHighScores.noviceRecord(), score, currentHighScores.expertRecord());
            case EXPERT -> new HighScoresDto(currentHighScores.noviceRecord(), currentHighScores.mediumRecord(), score);
        };

        write(updatedHighScores);
        return updatedHighScores;
    }

    public boolean isNewRecord(GameLevel gameLevel, int timeValue) {
        return isBetter(new ScoreDto("", timeValue), getScoreForLevel(load(), gameLevel));
    }

    private void ensureScoresFileExists() {
        try {
            Path parent = scoresFilePath.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            if (Files.notExists(scoresFilePath)) {
                write(DEFAULT_HIGH_SCORES);
            }
        } catch (IOException e) {
            //TODO: log
        }
    }

    private void write(HighScoresDto highScores) {
        try {
            objectWriter.writeValue(scoresFilePath.toFile(), highScores);
        } catch (IOException e) {
            //TODO: log
        }
    }

    private ScoreDto getScoreForLevel(HighScoresDto highScores, GameLevel gameLevel) {
        return switch (gameLevel) {
            case NOVICE -> highScores.noviceRecord();
            case MEDIUM -> highScores.mediumRecord();
            case EXPERT -> highScores.expertRecord();
        };
    }

    private boolean isBetter(ScoreDto newScore, ScoreDto currentScore) {
        return newScore.timeValue() < currentScore.timeValue();
    }
}
