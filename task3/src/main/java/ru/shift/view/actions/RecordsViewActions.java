package ru.shift.view.actions;

import java.util.function.Consumer;

public interface RecordsViewActions {
    void setSaveAction(Consumer<String> action);
}
