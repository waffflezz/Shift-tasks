package ru.shift.common.channel;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.exceptions.SerializeException;
import ru.shift.common.protocol.Message;
import ru.shift.common.serialization.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Реализация канала связи поверх TCP-сокета.
 * Использует JSON-сериализацию и передачу сообщений строками, разделёнными переводом строки.
 */
@Slf4j
public class Channel implements ChannelReader, ChannelWriter, AutoCloseable {
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;
    @Getter
    private final String clientId;

    /**
     * Создаёт канал на основе открытого сокета.
     *
     * @param socket TCP-сокет соединения
     * @throws IOException при ошибке создания потоков ввода-вывода
     */
    public Channel(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        this.clientId = socket.getRemoteSocketAddress().toString();
    }

    @Override
    public Message read() throws IOException, SerializeException {
        String line = reader.readLine();
        if (line == null) {
            return null;
        }
        return JsonSerializer.deserialize(line);
    }

    @Override
    public void send(Message message) throws SerializeException {
        String serialized = JsonSerializer.serialize(message);
        writer.println(serialized);
    }

    /**
     * Проверяет, активно ли соединение.
     *
     * @return true, если сокет подключён и не закрыт
     */
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    @Override
    public void close() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }

        try {
            reader.close();
        } catch (IOException e) {
            log.warn("Error while close reader. Error: {}", e.getMessage());
        }
        writer.close();
    }
}