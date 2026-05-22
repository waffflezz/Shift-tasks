package ru.shift.client.model.listeners;

import ru.shift.client.dto.AuthDto;

/**
 * Слушатель результата авторизации.
 */
@FunctionalInterface
public interface AuthListener extends ModelListener {
    /**
     * Вызывается при получении результата авторизации.
     *
     * @param authDto DTO с результатом
     */
    void onAuth(AuthDto authDto);
}
