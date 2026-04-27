package ru.shift.service;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ru.shift.Consumer;
import ru.shift.Producer;
import ru.shift.Storage;
import ru.shift.config.AppConfig;
import ru.shift.config.ConfigLoader;

@Slf4j
public class ProducerConsumerService {
    public void run(String configFileName) {
        AppConfig config = ConfigLoader.load(configFileName);
        runScenario(config);
    }

    private void runScenario(AppConfig config) {
        log.info("=== Старт: {} ===", config.scenarioName());

        Storage storage = new Storage(config.storageSize());
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= config.producerCount(); i++) {
            Producer producer = new Producer(i, storage, config.producerTimeMillis());
            Thread producerThread = new Thread(producer, "producer-" + i);
            producers.add(producer);
            threads.add(producerThread);
            producerThread.start();
        }

        for (int i = 1; i <= config.consumerCount(); i++) {
            Consumer consumer = new Consumer(i, storage, config.consumerTimeMillis());
            Thread consumerThread = new Thread(consumer, "consumer-" + i);
            consumers.add(consumer);
            threads.add(consumerThread);
            consumerThread.start();
        }

        try {
            Thread.sleep(config.scenarioDurationMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            producers.forEach(Producer::stop);
            consumers.forEach(Consumer::stop);

            for (Thread thread : threads) {
                thread.interrupt();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        log.info("=== Финиш: {} ===", config.scenarioName());
    }
}
