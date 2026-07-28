package ru.shift.cli;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ru.shift.service.ProducerConsumerService;

/**
 * Класс команды командной строки для запуска сценария производителей и потребителей.
 */
@Slf4j
@Command(
        name = "Task 5",
        mixinStandardHelpOptions = true,
        version = "1.0"
)
public class ProducerConsumerCommand implements Runnable {
    private final ProducerConsumerService producerConsumerService = new ProducerConsumerService();

    @Option(
            names = {"-c", "--config"},
            defaultValue = "default.properties",
            description = "Имя файла конфигурации из resources/configs"
    )
    private String configFileName;

    /**
     * Запускает сервис с выбранным конфигурационным файлом.
     */
    @Override
    public void run() {
        producerConsumerService.run(configFileName);
    }
}
