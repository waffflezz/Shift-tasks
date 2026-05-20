package ru.shift.client.model.listeners;

import ru.shift.client.dto.JoinUserDto;

public interface JoinUserListener extends ModelListener {
    void onJoin(JoinUserDto joinUserDto);
}
