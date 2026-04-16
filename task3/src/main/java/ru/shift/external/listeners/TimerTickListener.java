package ru.shift.external.listeners;

import ru.shift.dto.TimerTickDto;

@FunctionalInterface
public interface TimerTickListener extends ExternalListener {
    void onTimerTick(TimerTickDto timerTick);
}
