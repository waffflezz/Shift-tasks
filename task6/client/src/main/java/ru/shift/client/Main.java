package ru.shift.client;

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
