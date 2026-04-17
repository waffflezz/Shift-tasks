package ru.shift.view.views;

import ru.shift.external.listeners.ExternalListener;
import ru.shift.external.listeners.NewRecordListener;
import ru.shift.observers.ObserversRegistry;
import ru.shift.view.actions.RecordsViewActions;
import ru.shift.view.windows.RecordsWindow;

import javax.swing.*;
import java.awt.Window;
import java.util.function.Consumer;

/**
 * Показывает и обслуживает диалог сохранения нового рекорда.
 */
public class RecordsView implements RecordsViewActions, NewRecordListener {
    private final RecordsWindow recordsWindow;

    /**
     * Создаёт представление диалога рекорда и подписывает его на события рекордов.
     *
     * @param owner родительское окно
     * @param externalObservers реестр внешних наблюдателей
     */
    public RecordsView(Window owner, ObserversRegistry<ExternalListener> externalObservers) {
        recordsWindow = new RecordsWindow(owner);
        bindObservers(externalObservers);
    }

    /**
     * Показывает или скрывает диалог рекордов.
     *
     * @param visible признак видимости
     */
    public void setVisible(boolean visible) {
        SwingUtilities.invokeLater(() -> recordsWindow.setVisible(visible));
    }

    @Override
    public void setSaveAction(Consumer<String> action) {
        recordsWindow.setSaveHandler(action);
    }

    @Override
    public void onNewRecord() {
        setVisible(true);
    }

    /**
     * Подписывает это представление на уведомления о рекордах.
     *
     * @param externalObservers реестр внешних наблюдателей
     */
    private void bindObservers(ObserversRegistry<ExternalListener> externalObservers) {
        externalObservers.addListener(NewRecordListener.class, this);
    }
}
