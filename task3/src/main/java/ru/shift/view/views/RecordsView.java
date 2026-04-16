package ru.shift.view.views;

import lombok.Getter;
import ru.shift.controller.Controller;
import ru.shift.view.windows.RecordsWindow;

import java.awt.Window;

public class RecordsView {
    private final RecordsWindow recordsWindow;
    @Getter
    private String recordName;

    public RecordsView(Window owner, Controller controller) {
        recordsWindow = new RecordsWindow(owner);
        recordsWindow.setSaveHandler(name -> recordName = name);
    }

    public void setVisible(boolean visible) {
        recordsWindow.setVisible(visible);
    }

}
