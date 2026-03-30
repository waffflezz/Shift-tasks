package ru.shift.io;

import java.io.IOException;

public interface OutputWriter extends AutoCloseable {
    void write(String data) throws IOException;
}
