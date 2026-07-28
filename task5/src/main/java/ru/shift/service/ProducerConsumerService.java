package ru.shift.service;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ru.shift.actors.Actor;
import ru.shift.actors.Consumer;
import ru.shift.actors.Producer;
import ru.shift.storage.BoundedStorage;
import ru.shift.storage.Storage;
import ru.shift.config.AppConfig;
import ru.shift.config.ConfigLoader;

/**
 * Сервис запуска сценария с производителями, потребителями и общим хранилищем.
 */
@Slf4j
public class ProducerConsumerService {
    /**
     * Загружает конфигурацию и запускает сценарий.
     *
     * @param configFileName имя файла конфигурации
     */
    public void run(String configFileName) {
        AppConfig config = ConfigLoader.load(configFileName);
        runScenario(config);
    }

    /**
     * Выполняет сценарий по заданной конфигурации и корректно завершает все рабочие потоки.
     *
     * @param config параметры сценария
     */
    private void runScenario(AppConfig config) {
        log.info("=== Старт: {} ===", config.scenarioName());
        final int producerCount = config.producerCount();
        final int consumerCount = config.consumerCount();
        final int actorsCount = producerCount + consumerCount;

        Storage storage = new BoundedStorage(config.storageSize());
        List<Actor> actors = new ArrayList<>(actorsCount);
        List<Thread> threads = new ArrayList<>(actorsCount);

        for (int i = 1; i <= producerCount; i++) {
            Actor producer = new Producer(i, storage, config.producerTimeMillis());
            Thread producerThread = new Thread(producer, "producer-" + i);
            actors.add(producer);
            threads.add(producerThread);
            producerThread.start();
        }

        for (int i = 1; i <= consumerCount; i++) {
            Actor consumer = new Consumer(i, storage, config.consumerTimeMillis());
            Thread consumerThread = new Thread(consumer, "consumer-" + i);
            actors.add(consumer);
            threads.add(consumerThread);
            consumerThread.start();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            actors.forEach(Actor::stop);
            threads.forEach(Thread::interrupt);

            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                log.error("Ошибка во время завершения потоков", e);
            }

            log.info("=== Финиш: {} ===", config.scenarioName());
        }));
    }
}
