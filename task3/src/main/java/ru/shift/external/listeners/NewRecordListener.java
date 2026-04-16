package ru.shift.external.listeners;

@FunctionalInterface
public interface NewRecordListener extends ExternalListener {
    void onNewRecord();
}
