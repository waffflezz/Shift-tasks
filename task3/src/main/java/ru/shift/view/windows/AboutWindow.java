package ru.shift.view.windows;

import javax.swing.*;
import java.awt.*;

/**
 * Модальный диалог с информацией об игре.
 */
public class AboutWindow {
    private final JDialog dialog;

    /**
     * Создаёт диалог с информацией об игре.
     *
     * @param owner родительское окно
     */
    public AboutWindow(Window owner) {
        dialog = new JDialog(owner, "About", Dialog.ModalityType.APPLICATION_MODAL);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(createLabel("Игру сделал Скоробогатов Дмитрий", layout, 0));
        contentPane.add(createLabel("Копия оригинальной игры \"Сапёр\"", layout, 1));
        contentPane.add(createOkButton(layout));

        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(330, 130));
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
     * Создаёт текстовую метку.
     *
     * @param text текст метки
     * @param layout целевой компоновщик
     * @param gridY индекс строки
     * @return созданная метка
     */
    private JLabel createLabel(String text, GridBagLayout layout, int gridY) {
        JLabel label = new JLabel(text);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(gridY == 0 ? 10 : 5, 0, 0, 0);
        layout.setConstraints(label, gbc);

        return label;
    }

    /**
     * Создаёт кнопку закрытия диалога.
     *
     * @param layout целевой компоновщик
     * @return кнопка закрытия
     */
    private JButton createOkButton(GridBagLayout layout) {
        JButton okButton = new JButton("Ok");
        okButton.setPreferredSize(new Dimension(70, 25));
        okButton.addActionListener(e -> dialog.setVisible(false));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(okButton, gbc);

        return okButton;
    }
}
