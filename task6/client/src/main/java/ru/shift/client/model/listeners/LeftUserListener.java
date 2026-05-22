package ru.shift.client.model.listeners;

import ru.shift.client.dto.LeftUserDto;

/**
 * Слушатель отключения пользователя от чата.
 */
@FunctionalInterface
public interface LeftUserListener extends ModelListener {
    /**
     * Вызывается при отключении пользователя.
     *
     * @param leftUserDto DTO с информацией о пользователе
     */
    void onLeftUser(LeftUserDto leftUserDto);
}
