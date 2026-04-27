package ru.shift;

import picocli.CommandLine;
import ru.shift.cli.ProducerConsumerCommand;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new ProducerConsumerCommand()).execute(args);
        System.exit(exitCode);
    }
}
