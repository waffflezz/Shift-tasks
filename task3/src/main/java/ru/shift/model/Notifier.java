package ru.shift.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.dto.BombDto;
import ru.shift.dto.BombsGeneratedDto;
import ru.shift.dto.CellFlagChangedDto;
import ru.shift.dto.GameStartedDto;
import ru.shift.dto.OpenedCellDto;
import ru.shift.model.field.Cell;
import ru.shift.model.listeners.BombsGeneratedListener;
import ru.shift.model.listeners.CellFlagChangedListener;
import ru.shift.model.listeners.CellOpenListener;
import ru.shift.model.listeners.GameStartListener;
import ru.shift.model.listeners.GameStateChangedListener;
import ru.shift.model.listeners.ModelListener;
import ru.shift.observers.ObserversRegistry;

import java.util.List;

/**
 * Отвечает за публикацию событий игровой модели зарегистрированным слушателям.
 */
@Slf4j
@RequiredArgsConstructor
public class Notifier {
    private final ObserversRegistry<ModelListener> observers;

    /**
     * Публикует событие генерации мин на поле.
     *
     * @param bombs список координат сгенерированных мин
     */
    void notifyBombsGenerated(List<BombDto> bombs) {
        BombsGeneratedDto bombsGenerated = new BombsGeneratedDto(List.copyOf(bombs));
        observers.notifyListeners(BombsGeneratedListener.class, listener -> listener.onBombsGenerated(bombsGenerated));
    }

    /**
     * Публикует событие открытия клетки.
     *
     * @param cell открытая клетка
     */
    void notifyCellOpened(Cell cell) {
        OpenedCellDto openedCell = new OpenedCellDto(
                cell.getX(),
                cell.getY(),
                cell.isMined(),
                cell.getAdjacentMinesCount()
        );
        observers.notifyListeners(CellOpenListener.class, listener -> listener.onCellOpened(openedCell));
    }

    /**
     * Публикует событие запуска игры.
     */
    void notifyGameStarted(int width, int height, int minesCount) {
        GameStartedDto gameStarted = new GameStartedDto(width, height, minesCount);
        observers.notifyListeners(GameStartListener.class, listener -> listener.onGameStarted(gameStarted));
    }

    /**
     * Публикует текущее состояние игры.
     */
    void notifyGameStateChanged(GameState gameState) {
        log.info("Game state changed to {}", gameState);
        observers.notifyListeners(GameStateChangedListener.class, listener -> listener.onGameStateChanged(gameState));
    }

    /**
     * Публикует событие изменения флага на клетке.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @param isFlagged {@code true}, если флаг установлен, иначе {@code false}
     * @param remainingMinesCount оставшееся количество мин с учётом выставленных флагов
     */
    void notifyCellFlagChanged(int x, int y, boolean isFlagged, int remainingMinesCount) {
        CellFlagChangedDto cellFlagChanged = new CellFlagChangedDto(
                x,
                y,
                isFlagged,
                remainingMinesCount
        );
        observers.notifyListeners(CellFlagChangedListener.class, listener -> listener.onCellFlagChanged(cellFlagChanged));
    }
}
