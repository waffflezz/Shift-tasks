package ru.shift.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Validator {
    static void validate(AppConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("AppConfig не должен быть null");
        }

        if (config.scenarioName() == null || config.scenarioName().isBlank()) {
            throw new IllegalArgumentException("scenarioName должен быть задан");
        }

        if (config.producerCount() <= 0) {
            throw new IllegalArgumentException("producerCount должен быть больше 0");
        }

        if (config.consumerCount() <= 0) {
            throw new IllegalArgumentException("consumerCount должен быть больше 0");
        }

        if (config.producerTimeMillis() <= 0) {
            throw new IllegalArgumentException("producerTime должен быть больше 0");
        }

        if (config.consumerTimeMillis() <= 0) {
            throw new IllegalArgumentException("consumerTime должен быть больше 0");
        }

        if (config.storageSize() <= 0) {
            throw new IllegalArgumentException("storageSize должен быть больше 0");
        }

        if (config.scenarioDurationMillis() <= 0) {
            throw new IllegalArgumentException("scenarioDurationMillis должен быть больше 0");
        }
    }
}
