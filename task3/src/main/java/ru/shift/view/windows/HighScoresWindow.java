package ru.shift.view.windows;

import javax.swing.*;
import java.awt.*;

/**
 * Модальный диалог, отображающий лучшие результаты для каждого уровня сложности.
 */
public class HighScoresWindow {
    public static final String DEFAULT_RECORD_TEXT = "Unknown - 999";

    private final JDialog dialog;
    private final JLabel noviceRecordLabel;
    private final JLabel mediumRecordLabel;
    private final JLabel expertRecordLabel;

    /**
     * Создаёт диалог рекордов.
     *
     * @param owner родительское окно
     */
    public HighScoresWindow(Window owner) {
        dialog = new JDialog(owner, "High Scores", Dialog.ModalityType.APPLICATION_MODAL);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(layout);

        int gridY = 0;
        contentPane.add(createLabel("Novice:", layout, gridY++, 0));
        contentPane.add(noviceRecordLabel = createLabel(DEFAULT_RECORD_TEXT, layout, gridY++, 0));
        contentPane.add(createLabel("Medium:", layout, gridY++, 10));
        contentPane.add(mediumRecordLabel = createLabel(DEFAULT_RECORD_TEXT, layout, gridY++, 0));
        contentPane.add(createLabel("Expert:", layout, gridY++, 10));
        contentPane.add(expertRecordLabel = createLabel(DEFAULT_RECORD_TEXT, layout, gridY++, 0));
        contentPane.add(createCloseButton(layout));

        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(200, 200));
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
     * Обновляет текст рекорда для лёгкого режима.
     *
     * @param winnerName имя игрока
     * @param timeValue время рекорда
     */
    public void setNoviceRecord(String winnerName, int timeValue) {
        noviceRecordLabel.setText(createRecordText(winnerName, timeValue));
    }

    /**
     * Обновляет текст рекорда для среднего режима.
     *
     * @param winnerName имя игрока
     * @param timeValue время рекорда
     */
    public void setMediumRecord(String winnerName, int timeValue) {
        mediumRecordLabel.setText(createRecordText(winnerName, timeValue));
    }

    /**
     * Обновляет текст рекорда для сложного режима.
     *
     * @param winnerName имя игрока
     * @param timeValue время рекорда
     */
    public void setExpertRecord(String winnerName, int timeValue) {
        expertRecordLabel.setText(createRecordText(winnerName, timeValue));
    }

    /**
     * Формирует текст метки для рекорда.
     *
     * @param winnerName имя игрока
     * @param timeValue время рекорда
     * @return строка рекорда в нужном формате
     */
    private String createRecordText(String winnerName, int timeValue) {
        return winnerName + " - " + timeValue;
    }

    /**
     * Создаёт метку и размещает её в GridBagLayout.
     *
     * @param labelText текст метки
     * @param layout целевой компоновщик
     * @param gridY индекс строки
     * @param margin верхний отступ
     * @return созданная метка
     */
    private JLabel createLabel(String labelText, GridBagLayout layout, int gridY, int margin) {
        JLabel label = new JLabel(labelText);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(margin, 0, 0, 0);
        layout.setConstraints(label, gbc);

        return label;
    }

    /**
     * Создаёт кнопку закрытия диалога.
     *
     * @param layout целевой компоновщик
     * @return кнопка закрытия
     */
    private JButton createCloseButton(GridBagLayout layout) {
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.setVisible(false));
        okButton.setPreferredSize(new Dimension(60, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        layout.setConstraints(okButton, gbc);

        return okButton;
    }
}
