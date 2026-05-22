package ru.shift.common.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.shift.common.protocol.dto.notification.DisconnectNotificationDto;
import ru.shift.common.protocol.dto.notification.JoinNotificationDto;
import ru.shift.common.protocol.dto.notification.LeftNotificationDto;
import ru.shift.common.protocol.dto.notification.MessageNotificationDto;
import ru.shift.common.protocol.dto.request.LoginRequestDto;
import ru.shift.common.protocol.dto.request.MessageRequestDto;
import ru.shift.common.protocol.dto.request.UsersListRequestDto;
import ru.shift.common.protocol.dto.response.ErrorResponseDto;
import ru.shift.common.protocol.dto.response.LoginResponseDto;
import ru.shift.common.exceptions.SerializeException;
import ru.shift.common.protocol.Message;
import ru.shift.common.protocol.dto.response.MessageResponseDto;
import ru.shift.common.protocol.dto.response.UsersListResponseDto;
import ru.shift.common.protocol.impl.SocketNotification;
import ru.shift.common.protocol.impl.SocketRequest;
import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.SuccessResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonSerializer {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        registerMessages();
        registerBodies();
    }

    private static void registerMessages() {
        mapper.registerSubtypes(
                named(SocketRequest.class, JsonSerializationTypes.REQUEST),
                named(SuccessResponse.class, JsonSerializationTypes.SUCCESS),
                named(ErrorResponse.class, JsonSerializationTypes.ERROR),
                named(SocketNotification.class, JsonSerializationTypes.NOTIFICATION)
        );
    }

    private static void registerBodies() {
        mapper.registerSubtypes(
                named(LoginRequestDto.class, JsonSerializationTypes.LOGIN_REQUEST),
                named(LoginResponseDto.class, JsonSerializationTypes.LOGIN_RESPONSE),
                named(UsersListRequestDto.class, JsonSerializationTypes.USERS_LIST_REQUEST),
                named(UsersListResponseDto.class, JsonSerializationTypes.USERS_LIST_RESPONSE),
                named(MessageRequestDto.class, JsonSerializationTypes.MESSAGE_REQUEST),
                named(MessageResponseDto.class, JsonSerializationTypes.MESSAGE_RESPONSE),

                named(ErrorResponseDto.class, JsonSerializationTypes.ERROR_RESPONSE),

                named(JoinNotificationDto.class, JsonSerializationTypes.JOIN_NOTIFICATION),
                named(LeftNotificationDto.class, JsonSerializationTypes.LEFT_NOTIFICATION),
                named(DisconnectNotificationDto.class, JsonSerializationTypes.DISCONNECT_NOTIFICATION),
                named(MessageNotificationDto.class, JsonSerializationTypes.MESSAGE_NOTIFICATION)
        );
    }

    private static NamedType named(Class<?> clazz, JsonSerializationTypes type) {
        return new NamedType(clazz, type.name());
    }

    public static String serialize(Message message) throws SerializeException {
        try {
            return mapper.writeValueAsString(message);
        } catch (Exception e) {
            throw new SerializeException("Serialization failed", e);
        }
    }

    public static Message deserialize(String data) throws SerializeException {
        try {
            return mapper.readValue(data, Message.class);
        } catch (Exception e) {
            throw new SerializeException("Deserialization failed", e);
        }
    }
}