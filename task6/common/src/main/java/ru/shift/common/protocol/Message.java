package ru.shift.common.protocol;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "messageType"
)
public interface Message {
    String getId();

    void accept(MessageVisitor visitor);
}
