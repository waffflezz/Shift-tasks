package ru.shift.client.model.listeners;

import ru.shift.client.dto.LeftUserDto;

public interface LeftUserListener extends ModelListener {
    void onLeftUser(LeftUserDto leftUserDto);
}
