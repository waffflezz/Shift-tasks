package ru.shift.client.view.views;

import ru.shift.client.view.actions.AuthViewAction;
import ru.shift.client.view.actions.JoinViewActions;
import ru.shift.client.view.views.handlers.AuthHandler;
import ru.shift.client.view.windows.AuthWindow;
import ru.shift.client.view.windows.JoinWindow;

import java.awt.event.ActionListener;

public final class AuthView implements AuthViewAction {
    private final AuthWindow authWindow;

    public AuthView() {
        this.authWindow = new AuthWindow();
    }

    public void setVisible(boolean visible) {
        authWindow.setVisible(visible);
    }

    public void dispose() {
        authWindow.dispose();
    }

    public void showError(String error) {
        authWindow.showError(error);
    }

    @Override
    public void setAuthAction(AuthHandler handler) {
        authWindow.setAuthHandler(handler);
    }
}
