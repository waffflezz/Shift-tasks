package ru.shift.view.windows;

import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Модальный диалог для ввода имени игрока при новом рекорде.
 */
public class RecordsWindow {
    private final JDialog dialog;
    @Setter
    private Consumer<String> saveHandler;

    /**
     * Создаёт диалог рекорда.
     *
     * @param owner родительское окно
     */
    public RecordsWindow(Window owner) {
        dialog = new JDialog(owner, "New Record", Dialog.ModalityType.APPLICATION_MODAL);

        JTextField nameField = new JTextField();
        GridLayout layout = new GridLayout(3, 1);
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(new JLabel("Enter your name:"));
        contentPane.add(nameField);
        contentPane.add(createOkButton(nameField));

        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(210, 120));
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
    }

    /**
     * Показывает или скрывает диалог.
     *
     * @param visible признак видимости
     */
    public void setVisible(boolean visible) {
        dialog.setVisible(visible);
    }

    /**
     * Создаёт кнопку подтверждения диалога.
     *
     * @param nameField текстовое поле с именем игрока
     * @return кнопка подтверждения
     */
    private JButton createOkButton(JTextField nameField) {
        JButton button = new JButton("OK");
        button.addActionListener(e -> {
            dialog.setVisible(false);

            if (saveHandler != null) {
                saveHandler.accept(nameField.getText());
            }
        });
        return button;
    }
}
