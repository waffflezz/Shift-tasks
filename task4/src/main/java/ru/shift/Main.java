package ru.shift;

import picocli.CommandLine;
import ru.shift.cli.MultiThreadCommand;

public final class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new MultiThreadCommand()).execute(args);
        System.exit(exitCode);
    }
}
