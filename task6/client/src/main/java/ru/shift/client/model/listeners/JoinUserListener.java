package ru.shift.client.model.listeners;

import ru.shift.client.dto.JoinUserDto;

/**
 * Слушатель подключения нового пользователя к чату.
 */
@FunctionalInterface
public interface JoinUserListener extends ModelListener {
    /**
     * Вызывается при подключении нового пользователя.
     *
     * @param joinUserDto DTO с информацией о пользователе
     */
    void onJoin(JoinUserDto joinUserDto);
}
