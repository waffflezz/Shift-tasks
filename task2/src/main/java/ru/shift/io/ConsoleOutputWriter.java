package ru.shift.io;

public class ConsoleOutputWriter implements OutputWriter {
    @Override
    public void write(String data) {
        System.out.println(data);
    }

    @Override
    public void close() {}
}
