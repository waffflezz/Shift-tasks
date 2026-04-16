package ru.shift.view.views;

import ru.shift.external.listeners.ExternalListener;
import ru.shift.external.listeners.NewRecordListener;
import ru.shift.observer.ObserversRegistry;
import ru.shift.view.actions.RecordsViewActions;
import ru.shift.view.windows.RecordsWindow;

import javax.swing.*;
import java.awt.Window;
import java.util.function.Consumer;

public class RecordsView implements RecordsViewActions, NewRecordListener {
    private final RecordsWindow recordsWindow;

    public RecordsView(Window owner, ObserversRegistry<ExternalListener> externalObservers) {
        recordsWindow = new RecordsWindow(owner);
        bindObservers(externalObservers);
    }

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

    private void bindObservers(ObserversRegistry<ExternalListener> externalObservers) {
        externalObservers.addListener(NewRecordListener.class, this);
    }
}
