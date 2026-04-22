package ru.shift.view.windows;

import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Модальный диалог, показываемый при победе игрока.
 */
public class WinWindow {
    private final JDialog dialog;
    @Setter
    private ActionListener newGameAction;
    @Setter
    private ActionListener exitAction;

    /**
     * Создаёт диалог победы.
     *
     * @param owner родительское окно
     */
    public WinWindow(Window owner) {
        dialog = new JDialog(owner, "Win", Dialog.ModalityType.APPLICATION_MODAL);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(createTitleLabel(layout));
        contentPane.add(createNewGameButton(layout));
        contentPane.add(createExitButton(layout));

        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(300, 130));
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
     * Создаёт заголовок диалога.
     *
     * @param layout целевой компоновщик
     * @return метка заголовка
     */
    private JLabel createTitleLabel(GridBagLayout layout) {
        JLabel label = new JLabel("You win!");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        layout.setConstraints(label, gbc);

        return label;
    }

    /**
     * Создаёт кнопку запуска новой игры.
     *
     * @param layout целевой компоновщик
     * @return кнопка новой игры
     */
    private JButton createNewGameButton(GridBagLayout layout) {
        JButton newGameButton = new JButton("New game");
        newGameButton.setPreferredSize(new Dimension(100, 25));
        newGameButton.addActionListener(e -> {
            dialog.dispose();

            if (newGameAction != null) {
                newGameAction.actionPerformed(e);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(newGameButton, gbc);

        return newGameButton;
    }

    /**
     * Создаёт кнопку выхода из приложения.
     *
     * @param layout целевой компоновщик
     * @return кнопка выхода
     */
    private JButton createExitButton(GridBagLayout layout) {
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(100, 25));
        exitButton.addActionListener(e -> {
            dialog.setVisible(false);

            if (exitAction != null) {
                exitAction.actionPerformed(e);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(exitButton, gbc);

        return exitButton;
    }
}
