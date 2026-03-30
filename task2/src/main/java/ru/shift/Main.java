package ru.shift;

import picocli.CommandLine;
import ru.shift.cli.ShapeCommand;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new ShapeCommand()).execute(args);
        System.exit(exitCode);
    }
}
