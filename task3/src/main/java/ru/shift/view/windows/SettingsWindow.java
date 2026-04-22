package ru.shift.view.windows;

import lombok.Setter;
import ru.shift.view.types.GameType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Модальный диалог для выбора сложности следующей игры.
 */
public class SettingsWindow {
    private final JDialog dialog;
    private final Map<GameType, JRadioButton> radioButtonsMap = new HashMap<>(3);
    private final ButtonGroup radioGroup = new ButtonGroup();
    @Setter
    private Consumer<GameType> applyHandler;
    private GameType gameType;

    /**
     * Создаёт диалог настроек.
     *
     * @param owner родительское окно
     */
    public SettingsWindow(Window owner) {
        dialog = new JDialog(owner, "Settings", Dialog.ModalityType.APPLICATION_MODAL);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(layout);

        int gridY = 0;
        contentPane.add(createRadioButton("Novice (10 mines, 9x9)", GameType.NOVICE, layout, gridY++));
        contentPane.add(createRadioButton("Medium (40 mines, 16x16)", GameType.MEDIUM, layout, gridY++));
        contentPane.add(createRadioButton("Expert (99 mines, 16x30)", GameType.EXPERT, layout, gridY++));
        contentPane.add(createOkButton(layout));
        contentPane.add(createCloseButton(layout));

        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(300, 180));
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(owner);

        setSelectedGameType(GameType.NOVICE);
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
     * Выбирает указанный тип игры в диалоге.
     *
     * @param gameType тип игры для выбора
     */
    public void setSelectedGameType(GameType gameType) {
        JRadioButton radioButton = radioButtonsMap.get(gameType);

        if (radioButton == null) {
            throw new UnsupportedOperationException("Неизвестный тип игры: " + gameType);
        }

        this.gameType = gameType;
        radioGroup.setSelected(radioButton.getModel(), true);
    }

    /**
     * Создаёт радиокнопку для типа игры.
     *
     * @param radioButtonText отображаемый текст
     * @param gameType представляемый тип игры
     * @param layout целевой компоновщик
     * @param gridY индекс строки
     * @return созданная радиокнопка
     */
    private JRadioButton createRadioButton(String radioButtonText, GameType gameType, GridBagLayout layout, int gridY) {
        JRadioButton radioButton = new JRadioButton(radioButtonText);
        radioButton.addActionListener(e -> this.gameType = gameType);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        layout.setConstraints(radioButton, gbc);

        radioButtonsMap.put(gameType, radioButton);
        radioGroup.add(radioButton);

        return radioButton;
    }

    /**
     * Создаёт кнопку применения выбранных настроек.
     *
     * @param layout целевой компоновщик
     * @return кнопка применения
     */
    private JButton createOkButton(GridBagLayout layout) {
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(80, 25));
        okButton.addActionListener(e -> {
            dialog.setVisible(false);

            if (applyHandler != null) {
                applyHandler.accept(gameType);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(okButton, gbc);

        return okButton;
    }

    /**
     * Создаёт кнопку закрытия диалога без применения изменений.
     *
     * @param layout целевой компоновщик
     * @return кнопка отмены
     */
    private JButton createCloseButton(GridBagLayout layout) {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(80, 25));
        cancelButton.addActionListener(e -> dialog.setVisible(false));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(cancelButton, gbc);

        return cancelButton;
    }
}
