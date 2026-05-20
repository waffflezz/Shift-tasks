package ru.shift.server.session;

import ru.shift.server.kernel.SessionBroadcaster;

public record ServerContext(
        UserSessionRegistry users,
        SessionBroadcaster broadcaster
) {}
