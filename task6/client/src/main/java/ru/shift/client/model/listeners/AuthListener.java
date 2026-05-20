package ru.shift.client.model.listeners;

import ru.shift.client.dto.AuthDto;

public interface AuthListener extends ModelListener {
    void onAuth(AuthDto authDto);
}
