package ru.shift.observers;

import java.util.function.Consumer;

/**
 * Хранит слушателей по типам и уведомляет их.
 *
 * @param <T> общий базовый тип слушателя
 */
public interface ObserversRegistry<T> {
    /**
     * Регистрирует слушатель указанного типа.
     *
     * @param listenerType класс интерфейса слушателя
     * @param listener экземпляр слушателя
     * @param <L> конкретный тип слушателя
     */
    <L extends T> void addListener(Class<L> listenerType, L listener);

    /**
     * Удаляет слушатель указанного типа.
     *
     * @param listenerType класс интерфейса слушателя
     * @param listener экземпляр слушателя
     * @param <L> конкретный тип слушателя
     */
    <L extends T> void removeListener(Class<L> listenerType, L listener);

    /**
     * Уведомляет всех слушателей указанного типа.
     *
     * @param listenerType класс интерфейса слушателя
     * @param notifier функция уведомления, применяемая к каждому слушателю
     * @param <L> конкретный тип слушателя
     */
    <L extends T> void notifyListeners(Class<L> listenerType, Consumer<L> notifier);
}
