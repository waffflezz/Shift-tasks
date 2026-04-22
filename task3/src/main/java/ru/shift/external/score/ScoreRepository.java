package ru.shift.external.score;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.GameLevel;
import ru.shift.dto.HighScoresDto;
import ru.shift.dto.ScoreDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Читает и записывает рекорды в файл формата JSON.
 */
@Slf4j
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

    /**
     * Загружает рекорды из хранилища.
     *
     * @return сохранённые рекорды или значения по умолчанию при ошибке
     */
    public HighScoresDto load() {
        ensureScoresFileExists();

        try {
            return objectMapper.readValue(scoresFilePath.toFile(), HighScoresDto.class);
        } catch (IOException e) {
            log.warn("Error load from file {}, load default high score. Error: {}", scoresFilePath, e.getMessage());
            write(DEFAULT_HIGH_SCORES);
            return DEFAULT_HIGH_SCORES;
        }
    }

    /**
     * Сохраняет результат, если он лучше текущего для того же уровня.
     *
     * @param gameLevel уровень сложности
     * @param score кандидат на рекорд
     * @return итоговые рекорды после обновления
     */
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

    /**
     * Проверяет, станет ли указанное время новым рекордом.
     *
     * @param gameLevel уровень сложности
     * @param timeValue время прохождения в секундах
     * @return {@code true}, если результат лучше сохранённого
     */
    public boolean isNewRecord(GameLevel gameLevel, int timeValue) {
        return isBetter(new ScoreDto("", timeValue), getScoreForLevel(load(), gameLevel));
    }

    /**
     * Гарантирует существование файла рекордов и его родительских каталогов.
     */
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
            log.error("Error with path {}. Error: {}", scoresFilePath, e.getMessage());
        }
    }

    /**
     * Записывает рекорды в постоянное хранилище.
     *
     * @param highScores рекорды для сохранения
     */
    private void write(HighScoresDto highScores) {
        try {
            objectWriter.writeValue(scoresFilePath.toFile(), highScores);
        } catch (IOException e) {
            log.error("Error with write values to file {}. Error: {}", scoresFilePath, e.getMessage());
        }
    }

    /**
     * Возвращает рекорд, соответствующий указанному уровню сложности.
     *
     * @param highScores все сохранённые рекорды
     * @param gameLevel запрошенный уровень игры
     * @return рекорд для запрошенного уровня
     */
    private ScoreDto getScoreForLevel(HighScoresDto highScores, GameLevel gameLevel) {
        return switch (gameLevel) {
            case NOVICE -> highScores.noviceRecord();
            case MEDIUM -> highScores.mediumRecord();
            case EXPERT -> highScores.expertRecord();
        };
    }

    /**
     * Сравнивает два результата.
     *
     * @param newScore кандидат на рекорд
     * @param currentScore текущий рекорд
     * @return {@code true}, если новый результат лучше
     */
    private boolean isBetter(ScoreDto newScore, ScoreDto currentScore) {
        return newScore.timeValue() < currentScore.timeValue();
    }
}
