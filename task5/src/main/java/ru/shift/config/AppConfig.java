package ru.shift.config;

public record AppConfig(
        String scenarioName,
        int producerCount,
        int consumerCount,
        long producerTimeMillis,
        long consumerTimeMillis,
        int storageSize,
        long scenarioDurationMillis
) {}
