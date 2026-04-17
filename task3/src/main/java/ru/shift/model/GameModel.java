package ru.shift.model;

import ru.shift.model.contracts.CellChange;
import ru.shift.model.contracts.GameStarter;

/**
 * Объединяет все операции модели, доступные контроллеру.
 */
public interface GameModel extends CellChange, GameStarter {
}
