package ru.shift.client.view;

import ru.shift.client.model.listeners.ModelListener;
import ru.shift.client.observers.ObserversRegistry;
import ru.shift.client.view.windows.MainWindow;

/**
 * Основной слой представления, обновляющий главное игровое окно.
 */
public class MainView {

    private final MainWindow mainWindow;

    /**
     * Создаёт основное представление и подписывает его на события модели и внешние события.
     *
     * @param modelObservers реестр наблюдателей модели
     */
    public MainView(
            ObserversRegistry<ModelListener> modelObservers
    ) {
        mainWindow = new MainWindow();

        bindObservers(modelObservers);
    }

    /**
     * Показывает или скрывает главное окно.
     *
     * @param visible признак видимости
     */
    public void setVisible(boolean visible) {
        mainWindow.setVisible(visible);
    }

    /**
     * Подписывает представление на все необходимые наблюдатели.
     *
     * @param modelObservers реестр наблюдателей модели
     */
    private void bindObservers(
            ObserversRegistry<ModelListener> modelObservers
    ) {
    }
}
