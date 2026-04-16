package ru.shift.observer;

import java.util.function.Consumer;

public interface ObserversRegistry<T> {
    <L extends T> void addListener(Class<L> listenerType, L listener);

    <L extends T> void removeListener(Class<L> listenerType, L listener);

    <L extends T> void notifyListeners(Class<L> listenerType, Consumer<L> notifier);
}
