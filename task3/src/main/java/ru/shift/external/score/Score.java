package ru.shift.external.score;

import ru.shift.GameLevel;
import ru.shift.dto.GameStartedDto;
import ru.shift.dto.HighScoresDto;
import ru.shift.dto.ScoreDto;
import ru.shift.external.contracts.ScoreSaver;
import ru.shift.external.listeners.ExternalListener;
import ru.shift.external.listeners.HighScoresListener;
import ru.shift.external.listeners.NewRecordListener;
import ru.shift.external.timer.Timer;
import ru.shift.model.GameState;
import ru.shift.model.listeners.GameStartListener;
import ru.shift.model.listeners.GameStateChangedListener;
import ru.shift.model.listeners.ModelListener;
import ru.shift.observers.ObserversRegistry;

import java.util.Optional;

/**
 * Отслеживает рекорды завершённых игр и публикует связанные с ними события.
 */
public class Score implements GameStartListener, GameStateChangedListener, ScoreSaver {
    private static final String DEFAULT_PLAYER_NAME = "Anonymous";
    private static final int MAX_PLAYER_NAME_LENGTH = 32;

    private final ScoreRepository repository;
    private final ObserversRegistry<ModelListener> modelObservers;
    private final ObserversRegistry<ExternalListener> observers;
    private final Timer timer;

    private GameLevel currentGameLevel;
    private boolean scoreCanBeSaved;

    /**
     * Создаёт сервис работы с рекордами и подписывает его на события модели.
     *
     * @param repository хранилище рекордов
     * @param modelObservers реестр наблюдателей модели
     * @param observers реестр внешних наблюдателей
     * @param timer таймер, используемый для определения времени прохождения
     */
    public Score(
            ScoreRepository repository,
            ObserversRegistry<ModelListener> modelObservers,
            ObserversRegistry<ExternalListener> observers,
            Timer timer
    ) {
        this.repository = repository;
        this.modelObservers = modelObservers;
        this.observers = observers;
        this.timer = timer;

        bindObservers();
    }

    @Override
    public void onGameStarted(GameStartedDto gameStarted) {
        currentGameLevel = resolveGameLevel(gameStarted).orElse(GameLevel.NOVICE);
        scoreCanBeSaved = false;
    }

    @Override
    public void onGameStateChanged(GameState gameState) {
        if (gameState != GameState.WON) {
            scoreCanBeSaved = false;
            return;
        }

        if (currentGameLevel == null) {
            return;
        }

        int currentElapsedSeconds = timer.getSecondPassed();
        scoreCanBeSaved = repository.isNewRecord(currentGameLevel, currentElapsedSeconds);

        if (scoreCanBeSaved) {
            observers.notifyListeners(NewRecordListener.class, NewRecordListener::onNewRecord);
        }
    }

    @Override
    public void saveScore(String playerName) {
        if (!scoreCanBeSaved || currentGameLevel == null) {
            return;
        }

        int currentElapsedSeconds = timer.getSecondPassed();
        ScoreDto score = new ScoreDto(normalizePlayerName(playerName), currentElapsedSeconds);
        HighScoresDto highScores = repository.saveIfBetter(currentGameLevel, score);
        scoreCanBeSaved = false;
        notifyHighScoresChanged(highScores);
    }

    /**
     * Публикует текущие сохранённые рекорды.
     */
    public void publishHighScores() {
        notifyHighScoresChanged(repository.load());
    }

    /**
     * Уведомляет слушателей об изменении рекордов.
     *
     * @param highScores обновлённые рекорды
     */
    private void notifyHighScoresChanged(HighScoresDto highScores) {
        observers.notifyListeners(HighScoresListener.class, listener -> listener.onHighScoresChanged(highScores));
    }

    /**
     * Определяет уровень игры по параметрам поля из данных при старте.
     *
     * @param gameStarted данные о запущенной игре
     * @return найденный предопределённый уровень, если он существует
     */
    private Optional<GameLevel> resolveGameLevel(GameStartedDto gameStarted) {
        for (GameLevel gameLevel : GameLevel.values()) {
            if (gameLevel.getWidth() == gameStarted.width()
                    && gameLevel.getHeight() == gameStarted.height()
                    && gameLevel.getMinesCount() == gameStarted.minesCount()) {
                return Optional.of(gameLevel);
            }
        }
        return Optional.empty();
    }

    /**
     * Нормализует имя игрока перед сохранением результата.
     *
     * @param playerName исходное имя игрока
     * @return нормализованное имя игрока
     */
    private String normalizePlayerName(String playerName) {
        if (playerName == null) {
            return DEFAULT_PLAYER_NAME;
        }

        String normalizedPlayerName = playerName.trim();

        if (normalizedPlayerName.isEmpty()) {
            return DEFAULT_PLAYER_NAME;
        }

        if (normalizedPlayerName.length() > MAX_PLAYER_NAME_LENGTH) {
            return normalizedPlayerName.substring(0, MAX_PLAYER_NAME_LENGTH);
        }

        return normalizedPlayerName;
    }

    /**
     * Подписывает этот сервис на события модели.
     */
    private void bindObservers() {
        modelObservers.addListener(GameStateChangedListener.class, this);
        modelObservers.addListener(GameStartListener.class, this);
    }
}
