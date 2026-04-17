package ru.shift.external.listeners;

import ru.shift.dto.TimerTickDto;

/**
 * Получает обновления таймера.
 */
@FunctionalInterface
public interface TimerTickListener extends ExternalListener {
    /**
     * Обрабатывает тик таймера.
     *
     * @param timerTick текущее состояние таймера
     */
    void onTimerTick(TimerTickDto timerTick);
}
