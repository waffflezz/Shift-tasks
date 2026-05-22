package ru.shift.client.model.listeners;

import java.util.List;

/**
 * Слушатель получения списка пользователей чата.
 */
@FunctionalInterface
public interface UsersListListener extends ModelListener {
    /**
     * Вызывается при получении списка пользователей.
     *
     * @param usernames список никнеймов
     */
    void onUsersList(List<String> usernames);
}
