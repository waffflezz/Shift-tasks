package ru.shift.io;

import java.io.IOException;

public interface InputReader extends AutoCloseable {
    String readLine(int maxLength) throws IOException;
}
