package ru.shift.client.view.views;

import ru.shift.client.view.actions.JoinViewActions;
import ru.shift.client.view.views.handlers.ConnectionHandler;
import ru.shift.client.view.windows.JoinWindow;

/**
 * Представление окна подключения к серверу.
 * Делегирует все операции окну {@link JoinWindow}.
 */
public final class JoinView implements JoinViewActions {
    private final JoinWindow joinWindow;

    public JoinView() {
        this.joinWindow = new JoinWindow();
    }

    /**
     * Показывает или скрывает окно подключения.
     *
     * @param visible признак видимости
     */
    public void setVisible(boolean visible) {
        joinWindow.setVisible(visible);
    }

    /**
     * Закрывает окно подключения.
     */
    public void dispose() {
        joinWindow.dispose();
    }

    /**
     * Отображает ошибку в окне подключения.
     *
     * @param error текст ошибки
     */
    public void showError(String error) {
        joinWindow.showError(error);
    }

    @Override
    public void setConnectionAction(ConnectionHandler handler) {
        joinWindow.setConnectionHandler(handler);
    }
}
