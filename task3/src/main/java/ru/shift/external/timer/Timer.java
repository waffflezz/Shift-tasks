package ru.shift.external.timer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.dto.TimerTickDto;
import ru.shift.external.listeners.ExternalListener;
import ru.shift.external.listeners.TimerTickListener;
import ru.shift.model.GameState;
import ru.shift.model.listeners.GameStateChangedListener;
import ru.shift.model.listeners.ModelListener;
import ru.shift.observers.ObserversRegistry;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Отслеживает прошедшее игровое время и публикует тики таймера.
 */
@Slf4j
public class Timer implements GameStateChangedListener {
    private static final long TIMER_TICK_PERIOD_MILLIS = 500;

    private final ObserversRegistry<ExternalListener> externalObservers;
    private final ObserversRegistry<ModelListener> modelObservers;

    private ScheduledExecutorService scheduler;

    @Getter
    private volatile int secondPassed;

    /**
     * Создаёт таймер и подписывает его на изменения состояния модели.
     *
     * @param externalObservers реестр внешних наблюдателей
     * @param modelObservers реестр наблюдателей модели
     */
    public Timer(
            ObserversRegistry<ExternalListener> externalObservers,
            ObserversRegistry<ModelListener> modelObservers
    ) {
        this.externalObservers = externalObservers;
        this.modelObservers = modelObservers;

        bindObservers();
    }

    @Override
    public void onGameStateChanged(GameState gameState) {
        switch (gameState) {
            case IN_PROGRESS -> start();
            case WON, LOST -> stop();
            case NEW -> reset();
        }
    }

    /**
     * Запускает или перезапускает обновления таймера.
     */
    private void start() {
        if (scheduler != null && !scheduler.isShutdown()) {
            stop();
        }

        var startTimeNanos = Instant.now();

        secondPassed = 0;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                long elapsedTime = Instant.now().toEpochMilli() - startTimeNanos.toEpochMilli();
                int newSeconds = (int) (elapsedTime / 1000);

                if (newSeconds > secondPassed) {
                    secondPassed = newSeconds;
                    notifyTimerTick(secondPassed);
                }
            } catch (Exception e) {
                log.error("Error start timer. Error: {}", e.getMessage());
            }
        }, TIMER_TICK_PERIOD_MILLIS, TIMER_TICK_PERIOD_MILLIS, TimeUnit.MILLISECONDS);
    }

    /**
     * Останавливает обновления таймера.
     */
    private void stop() {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }

    /**
     * Сбрасывает прошедшее время и публикует нулевое значение.
     */
    private void reset() {
        stop();
        secondPassed = 0;
        notifyTimerTick(secondPassed);
    }

    /**
     * Публикует событие тика таймера.
     *
     * @param currentElapsedSeconds прошедшее время в секундах
     */
    private void notifyTimerTick(int currentElapsedSeconds) {
        TimerTickDto timerTick = new TimerTickDto(currentElapsedSeconds);
        externalObservers.notifyListeners(TimerTickListener.class, listener -> listener.onTimerTick(timerTick));
    }

    /**
     * Подписывает этот таймер на события модели.
     */
    private void bindObservers() {
        modelObservers.addListener(GameStateChangedListener.class, this);
    }
}
