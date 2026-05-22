package ru.shift.client;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.controller.MainController;
import ru.shift.client.model.ChatMainModel;
import ru.shift.client.model.ChatModel;
import ru.shift.client.model.connection.ClientService;
import ru.shift.client.model.listeners.ModelListener;
import ru.shift.client.observers.ObserversByTypeRegistry;
import ru.shift.client.view.MainView;

import java.io.IOException;

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
        Client client = new Client();
        client.start();
    }
}
