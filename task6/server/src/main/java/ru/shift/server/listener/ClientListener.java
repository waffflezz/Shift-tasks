package ru.shift.server.listener;

import ru.shift.common.protocol.Message;
import ru.shift.server.kernel.Dispatcher;
import ru.shift.server.kernel.ServerVisitor;
import ru.shift.server.session.ClientSession;

import java.net.Socket;

public class ClientListener implements Runnable {
    private final Socket socket;
    private final Dispatcher dispatcher;

    public ClientListener(Socket socket, Dispatcher dispatcher) {
        this.socket = socket;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try (ClientSession session = new ClientSession(socket)) {
            while (!session.isClosed()) {
                Message message = session.getReader().read();
                if (message != null) {
                    message.accept(new ServerVisitor(dispatcher, session));
                }
            }
        } catch (Exception e) {
            //TODO: log
        }
    }
}
