package ru.shift.client.view.views;

import ru.shift.client.view.actions.JoinViewActions;
import ru.shift.client.view.views.handlers.ConnectionHandler;
import ru.shift.client.view.windows.JoinWindow;

public final class JoinView implements JoinViewActions {
    private final JoinWindow joinWindow;

    public JoinView() {
        this.joinWindow = new JoinWindow();
    }

    public void setVisible(boolean visible) {
        joinWindow.setVisible(visible);
    }

    public void dispose() {
        joinWindow.dispose();
    }

    public void showError(String error) {
        joinWindow.showError(error);
    }

    @Override
    public void setConnectionAction(ConnectionHandler handler) {
        joinWindow.setConnectionHandler(handler);
    }
}
