package ru.shift.io;

import lombok.extern.slf4j.Slf4j;
import ru.shift.constants.IOConstants;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
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
        while (builder.length() < maxLength && (currChar = reader.read()) != IOConstants.EOF) {
            if (currChar == '\n') {
                break;
            }
            if (currChar != '\r') {
                builder.append((char) currChar);
            }
        }

        return builder.toString().trim();
    }

    @Override
    public void close() throws Exception {
        try {
            reader.close();
        } catch (IOException e) {
            log.warn("При попытке закрыть BufferedReader произошла ошибка: {}", e.getMessage());
        }
    }
}
