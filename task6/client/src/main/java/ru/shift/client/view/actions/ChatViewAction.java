package ru.shift.client.view.actions;

import java.awt.event.ActionListener;

public interface ChatViewAction {
    void setSendMessageAction(ActionListener listener);
    void setMessageInputAction(ActionListener listener);
    void setWindowCloseAction(ActionListener listener);
}
