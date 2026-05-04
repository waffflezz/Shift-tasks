package ru.shift.service;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ru.shift.actors.Actor;
import ru.shift.storage.BoundedStorage;
import ru.shift.storage.Storage;
import ru.shift.config.AppConfig;
import ru.shift.config.ConfigLoader;
import ru.shift.factories.ActorFactory;
import ru.shift.factories.ConsumerFactory;
import ru.shift.factories.ProducerFactory;

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

        Storage storage = new BoundedStorage(config.storageSize());
        ActorFactory producerFactory = new ProducerFactory();
        ActorFactory consumerFactory = new ConsumerFactory();
        List<Actor> actors = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= config.producerCount(); i++) {
            Actor producer = producerFactory.create(i, storage, config.producerTimeMillis());
            Thread producerThread = new Thread(producer, "producer-" + i);
            actors.add(producer);
            threads.add(producerThread);
            producerThread.start();
        }

        for (int i = 1; i <= config.consumerCount(); i++) {
            Actor consumer = consumerFactory.create(i, storage, config.consumerTimeMillis());
            Thread consumerThread = new Thread(consumer, "consumer-" + i);
            actors.add(consumer);
            threads.add(consumerThread);
            consumerThread.start();
        }

        try {
            Thread.sleep(config.scenarioDurationMillis());
        } catch (InterruptedException e) {
            log.error("Ошибка во время ожидания завершения сценария", e);
        } finally {
            actors.forEach(Actor::stop);

            for (Thread thread : threads) {
                thread.interrupt();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    log.error("Ошибка во время ожидания завершения рабочего потока", e);
                    break;
                }
            }
        }

        log.info("=== Финиш: {} ===", config.scenarioName());
    }
}
