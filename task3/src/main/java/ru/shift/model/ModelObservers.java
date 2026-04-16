package ru.shift.model;

import ru.shift.model.listeners.ModelListener;
import ru.shift.observer.ObserversRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ModelObservers implements ObserversRegistry<ModelListener> {
    private final Map<Class<? extends ModelListener>, List<ModelListener>> listenersByType = new HashMap<>();

    @Override
    public <L extends ModelListener> void addListener(Class<L> listenerType, L listener) {
        listenersByType
                .computeIfAbsent(listenerType, key -> new ArrayList<>())
                .add(listener);
    }

    @Override
    public <L extends ModelListener> void removeListener(Class<L> listenerType, L listener) {
        List<ModelListener> listeners = listenersByType.get(listenerType);

        if (listeners == null) {
            return;
        }

        listeners.remove(listener);

        if (listeners.isEmpty()) {
            listenersByType.remove(listenerType);
        }
    }

    @Override
    public <L extends ModelListener> void notifyListeners(Class<L> listenerType, Consumer<L> notifier) {
        List<ModelListener> listeners = listenersByType.get(listenerType);

        if (listeners == null) {
            return;
        }

        for (ModelListener listener : listeners) {
            notifier.accept(listenerType.cast(listener));
        }
    }
}
