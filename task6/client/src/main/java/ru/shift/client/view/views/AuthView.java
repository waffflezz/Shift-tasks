package ru.shift.client.view.views;

import ru.shift.client.view.actions.AuthViewAction;
import ru.shift.client.view.views.handlers.AuthHandler;
import ru.shift.client.view.windows.AuthWindow;

/**
 * Представление окна авторизации.
 * Делегирует все операции окну {@link AuthWindow}.
 */
public final class AuthView implements AuthViewAction {
    private final AuthWindow authWindow;

    public AuthView() {
        this.authWindow = new AuthWindow();
    }

    /**
     * Показывает или скрывает окно авторизации.
     *
     * @param visible признак видимости
     */
    public void setVisible(boolean visible) {
        authWindow.setVisible(visible);
    }

    /**
     * Закрывает окно авторизации.
     */
    public void dispose() {
        authWindow.dispose();
    }

    /**
     * Отображает ошибку в окне авторизации.
     *
     * @param error текст ошибки
     */
    public void showError(String error) {
        authWindow.showError(error);
    }

    @Override
    public void setAuthAction(AuthHandler handler) {
        authWindow.setAuthHandler(handler);
    }
}
