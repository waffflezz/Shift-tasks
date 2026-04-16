package ru.shift.external;

import ru.shift.external.listeners.ExternalListener;
import ru.shift.observer.ObserversRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ExternalObservers implements ObserversRegistry<ExternalListener> {
    private final Map<Class<? extends ExternalListener>, List<ExternalListener>> listenersByType = new HashMap<>();

    @Override
    public <L extends ExternalListener> void addListener(Class<L> listenerType, L listener) {
        listenersByType
                .computeIfAbsent(listenerType, key -> new ArrayList<>())
                .add(listener);
    }

    @Override
    public <L extends ExternalListener> void removeListener(Class<L> listenerType, L listener) {
        List<ExternalListener> listeners = listenersByType.get(listenerType);

        if (listeners == null) {
            return;
        }

        listeners.remove(listener);

        if (listeners.isEmpty()) {
            listenersByType.remove(listenerType);
        }
    }

    @Override
    public <L extends ExternalListener> void notifyListeners(Class<L> listenerType, Consumer<L> notifier) {
        List<ExternalListener> listeners = listenersByType.get(listenerType);

        if (listeners == null) {
            return;
        }

        for (ExternalListener listener : listeners) {
            notifier.accept(listenerType.cast(listener));
        }
    }
}
