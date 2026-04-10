package ru.shift.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerUtil {
    public static void logErrorWithDebug(String message, Exception e) {
        if (log.isDebugEnabled()) log.debug(message, e);
        else log.error("{}: {}", message, e.getMessage());
    }
}
