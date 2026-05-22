package ru.shift.common.protocol;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Базовый интерфейс для всех сообщений протокола.
 * Аннотация Jackson обеспечивает полиморфную сериализацию с полем messageType.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "messageType"
)
public interface Message {
    /**
     * Возвращает уникальный идентификатор сообщения.
     *
     * @return идентификатор
     */
    String getId();

    /**
     * Принимает посетителя для диспетчеризации по типу сообщения.
     *
     * @param visitor посетитель
     */
    void accept(MessageVisitor visitor);
}
