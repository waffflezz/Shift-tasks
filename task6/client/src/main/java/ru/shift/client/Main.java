package ru.shift.client;

import ru.shift.client.controller.MainController;
import ru.shift.client.model.ChatMainModel;
import ru.shift.client.model.ChatModel;
import ru.shift.client.model.connection.ClientService;
import ru.shift.client.model.listeners.ModelListener;
import ru.shift.client.observers.ObserversByTypeRegistry;
import ru.shift.client.view.MainView;

/**
 * Точка входа в приложение.
 */
public class Main {
    /**
     * Создаёт компоненты приложения и запускает начальную игру.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        var modelObservers = new ObserversByTypeRegistry<ModelListener>();

        var clientService = new ClientService();
        ChatModel model = new ChatMainModel(modelObservers, clientService);
        MainView view = new MainView(modelObservers);
        var controller = new MainController(model, view);

        controller.start();
    }
}
