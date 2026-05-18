package ru.shift.config;

/**
 * Конфигурация сценария запуска производителей и потребителей.
 *
 * @param scenarioName имя сценария
 * @param producerCount количество производителей
 * @param consumerCount количество потребителей
 * @param producerTimeMillis время производства одного ресурса в миллисекундах
 * @param consumerTimeMillis время потребления одного ресурса в миллисекундах
 * @param storageSize максимальный размер хранилища
 */
public record AppConfig(
        String scenarioName,
        int producerCount,
        int consumerCount,
        long producerTimeMillis,
        long consumerTimeMillis,
        int storageSize
) {}
