package ru.shift.client.model.listeners;

import java.util.List;

public interface UsersListListener extends ModelListener {
    void onUsersList(List<String> usernames);
}
