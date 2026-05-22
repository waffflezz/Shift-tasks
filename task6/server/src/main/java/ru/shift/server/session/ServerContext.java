package ru.shift.server.session;

import ru.shift.server.kernel.SessionBroadcaster;

/**
 * Контекст сервера, предоставляющий доступ к реестру пользователей и механизму broadcast.
 *
 * @param users реестр пользователей
 * @param broadcaster компонент широковещательной рассылки
 */
public record ServerContext(
        UserSessionRegistry users,
        SessionBroadcaster broadcaster
) {}
