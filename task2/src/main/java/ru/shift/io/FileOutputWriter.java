package ru.shift.io;

import ru.shift.constants.Messages;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileOutputWriter implements OutputWriter {
    private final BufferedWriter writer;

    public FileOutputWriter(String fileName) throws FileNotFoundException {
        this.writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(fileName),
                        StandardCharsets.UTF_8
                )
        );
    }

    @Override
    public void write(String data) throws IOException {
        writer.write(data);
        writer.flush();
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            //TODO: logger
        }
    }
}
