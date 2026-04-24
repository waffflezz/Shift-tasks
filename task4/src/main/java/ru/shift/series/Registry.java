package ru.shift.series;

import java.util.Collection;
import java.util.Optional;

/**
 * Обобщённый интерфейс реестра объектов с доступом по ключу.
 *
 * @param <T> тип хранимого объекта
 * @param <K> тип ключа доступа
 */
public interface Registry<T, K> {
    /**
     * Возвращает все зарегистрированные объекты.
     *
     * @return коллекция объектов реестра
     */
    Collection<T> getAll();

    /**
     * Ищет объект по ключу.
     *
     * @param key ключ поиска
     * @return найденный объект либо пустой результат
     */
    Optional<T> find(K key);

    /**
     * Регистрирует объект в реестре.
     *
     * @param item объект для регистрации
     */
    void register(T item);

    /**
     * Удаляет объект из реестра по ключу.
     *
     * @param key ключ удаляемого объекта
     * @return удалённый объект либо пустой результат
     */
    Optional<T> remove(K key);
}
