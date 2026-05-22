package ru.shift.client;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.client.controller.MainController;
import ru.shift.client.model.ChatMainModel;
import ru.shift.client.model.ChatModel;
import ru.shift.client.model.connection.ClientService;
import ru.shift.client.model.listeners.ModelListener;
import ru.shift.client.observers.ObserversByTypeRegistry;
import ru.shift.client.view.MainView;

import java.io.IOException;

@Slf4j
@NoArgsConstructor
public final class Client {
    private final ClientService clientService = new ClientService();

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        var observers = new ObserversByTypeRegistry<ModelListener>();
        ChatModel model = new ChatMainModel(observers, clientService);
        MainView view = new MainView(observers);
        var controller = new MainController(model, view);

        controller.start();
    }

    private void shutdown() {
        log.info("Application shutting down, closing connection");
        try {
            clientService.close();
        } catch (IOException e) {
            log.warn("Can't closing connection. Error: {}", e.getMessage());
        }
    }
}
