package ru.shift.io;

import ru.shift.constants.Messages;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static ru.shift.constants.IOConstants.EOF;

public class FileInputReader implements InputReader {
    private final BufferedReader reader;

    public FileInputReader(String filePath) throws FileNotFoundException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath),
                        StandardCharsets.UTF_8
                )
        );
    }

    @Override
    public String readLine(int maxLength) throws IOException {
        StringBuilder builder = new StringBuilder(maxLength);
        int currChar;
        while (builder.length() < maxLength && (currChar = reader.read()) != EOF) {
            if (currChar == '\n') {
                break;
            }
            if (currChar != '\r') {
                builder.append(currChar);
            }
        }

        return builder.toString().trim();
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            //TODO: Логгер!
        }
    }
}
