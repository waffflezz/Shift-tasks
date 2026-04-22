package ru.shift.view.actions;

import java.util.function.Consumer;

/**
 * Описывает действия, доступные в диалоге рекорда.
 */
public interface RecordsViewActions {
    /**
     * Устанавливает обработчик сохранения имени игрока.
     *
     * @param action обработчик сохранения
     */
    void setSaveAction(Consumer<String> action);
}
