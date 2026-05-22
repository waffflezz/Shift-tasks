package ru.shift.common.protocol.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Маркерный интерфейс для всех тел сообщений протокола.
 * Аннотация Jackson обеспечивает полиморфную сериализацию с полем bodyType.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "bodyType"
)
public interface Body {
}
