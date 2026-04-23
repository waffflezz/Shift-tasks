package ru.shift.series;

import java.util.Collection;
import java.util.Optional;

public interface Registry<T, K> {
    Collection<T> getAll();

    Optional<T> find(K key);

    void register(T item);

    Optional<T> remove(K key);
}
