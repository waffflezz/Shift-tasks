package ru.shift.client;

import ru.shift.client.view.windows.JoinWindow;
import ru.shift.client.view.windows.MainWindow;

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
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}
