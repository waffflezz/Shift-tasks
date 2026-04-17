package ru.shift.observers;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Хранит слушателей, сгруппированных по типу их интерфейса.
 *
 * @param <T> общий базовый тип слушателя
 */
@Slf4j
public class ObserversByTypeRegistry<T> implements ObserversRegistry<T> {
    private final Map<Class<? extends T>, List<T>> listenersByType = new HashMap<>();

    @Override
    public <L extends T> void addListener(Class<L> listenerType, L listener) {
        listenersByType
                .computeIfAbsent(listenerType, key -> new ArrayList<>())
                .add(listener);
    }

    @Override
    public <L extends T> void removeListener(Class<L> listenerType, L listener) {
        List<T> listeners = listenersByType.get(listenerType);

        if (listeners == null) {
            return;
        }

        listeners.remove(listener);

        if (listeners.isEmpty()) {
            listenersByType.remove(listenerType);
        }
    }

    @Override
    public <L extends T> void notifyListeners(Class<L> listenerType, Consumer<L> notifier) {
        List<T> listeners = listenersByType.get(listenerType);

        if (listeners == null) {
            return;
        }

        for (T listener : listeners) {
            try {
                notifier.accept(listenerType.cast(listener));
            } catch (Exception e) {
                log.warn("Error in listener {}, with type {}. Error: {}", listener, listenerType, e.getMessage());
            }
        }
    }
}
