package ru.shift.view.windows;

import lombok.Setter;
import ru.shift.view.types.ButtonType;
import ru.shift.view.types.GameImage;
import ru.shift.view.views.CellClickHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Главное окно приложения, отображающее поле и меню.
 */
public class MainWindow {
    private final JFrame frame;
    private final Container contentPane;
    private final GridBagLayout mainLayout;
    private final AboutWindow aboutWindow;

    private JMenuItem newGameMenu;
    private JMenuItem highScoresMenu;
    private JMenuItem settingsMenu;

    @Setter
    private CellClickHandler cellClickHandler;
    private JButton[][] cellButtons;
    private JLabel timerLabel;
    private JLabel bombsCounterLabel;

    /**
     * Создаёт главное окно и его элементы интерфейса.
     */
    public MainWindow() {
        frame = new JFrame("Miner");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        aboutWindow = new AboutWindow(frame);

        createMenu();

        contentPane = frame.getContentPane();
        mainLayout = new GridBagLayout();
        contentPane.setLayout(mainLayout);
        contentPane.setBackground(new Color(144, 158, 184));
    }

    /**
     * Возвращает корневой экземпляр окна.
     *
     * @return корневое окно
     */
    public Window getWindow() {
        return frame;
    }

    /**
     * Показывает или скрывает главное окно.
     *
     * @param visible признак видимости
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    /**
     * Освобождает ресурсы главного окна.
     */
    public void dispose() {
        frame.dispose();
    }

    /**
     * Устанавливает обработчик для пункта меню новой игры.
     *
     * @param listener обработчик действия
     */
    public void setNewGameMenuAction(ActionListener listener) {
        newGameMenu.addActionListener(listener);
    }

    /**
     * Устанавливает обработчик для пункта меню рекордов.
     *
     * @param listener обработчик действия
     */
    public void setHighScoresMenuAction(ActionListener listener) {
        highScoresMenu.addActionListener(listener);
    }

    /**
     * Устанавливает обработчик для пункта меню настроек.
     *
     * @param listener обработчик действия
     */
    public void setSettingsMenuAction(ActionListener listener) {
        settingsMenu.addActionListener(listener);
    }

    /**
     * Устанавливает изображение на клетку поля.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @param gameImage изображение для отображения
     */
    public void setCellImage(int x, int y, GameImage gameImage) {
        cellButtons[y][x].setIcon(gameImage.getImageIcon());
    }

    /**
     * Обновляет счётчик мин.
     *
     * @param bombsCount отображаемое количество мин
     */
    public void setBombsCount(int bombsCount) {
        bombsCounterLabel.setText(String.valueOf(bombsCount));
    }

    /**
     * Обновляет текст таймера.
     *
     * @param value прошедшее время в секундах
     */
    public void setTimerValue(int value) {
        timerLabel.setText(String.valueOf(value));
    }

    /**
     * Пересоздаёт игровое поле под указанные размеры.
     *
     * @param rowsCount количество строк
     * @param colsCount количество столбцов
     */
    public void createGameField(int rowsCount, int colsCount) {
        contentPane.removeAll();
        frame.setPreferredSize(new Dimension(20 * colsCount + 70, 20 * rowsCount + 110));

        addButtonsPanel(createButtonsPanel(rowsCount, colsCount));
        addTimerImage();
        addTimerLabel(timerLabel = new JLabel("0"));
        addBombCounter(bombsCounterLabel = new JLabel("0"));
        addBombCounterImage();
        frame.pack();
        frame.setLocationRelativeTo(null);
        contentPane.revalidate();
        contentPane.repaint();
    }

    /**
     * Создаёт меню приложения.
     */
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem aboutMenu = new JMenuItem("About");
        aboutMenu.addActionListener(e -> aboutWindow.setVisible(true));

        gameMenu.add(newGameMenu = new JMenuItem("New Game"));
        gameMenu.addSeparator();
        gameMenu.add(highScoresMenu = new JMenuItem("High Scores"));
        gameMenu.add(settingsMenu = new JMenuItem("Settings"));
        gameMenu.addSeparator();
        gameMenu.add(aboutMenu);

        JMenuItem exitMenu = new JMenuItem("Exit");
        gameMenu.add(exitMenu);
        exitMenu.addActionListener(e -> dispose());

        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);
    }

    /**
     * Создаёт панель, заполненную кнопками клеток.
     *
     * @param numberOfRows количество строк поля
     * @param numberOfCols количество столбцов поля
     * @return панель с кнопками клеток
     */
    private JPanel createButtonsPanel(int numberOfRows, int numberOfCols) {
        cellButtons = new JButton[numberOfRows][numberOfCols];
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(20 * numberOfCols, 20 * numberOfRows));
        buttonsPanel.setLayout(new GridLayout(numberOfRows, numberOfCols, 0, 0));

        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfCols; col++) {
                final int x = col;
                final int y = row;
                JButton button = new JButton(GameImage.CLOSED.getImageIcon());
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent event) {
                        handleCellClick(x, y, event);
                    }
                });
                cellButtons[y][x] = button;
                buttonsPanel.add(button);
            }
        }

        return buttonsPanel;
    }

    /**
     * Обрабатывает событие мыши для клетки поля.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @param event событие мыши
     */
    private void handleCellClick(int x, int y, MouseEvent event) {
        if (cellClickHandler == null) {
            return;
        }

        cellClickHandler.onCellClick(x, y, resolveButtonType(event));
    }

    /**
     * Определяет доменный тип кнопки по событию мыши.
     *
     * @param event событие мыши
     * @return определённый тип кнопки
     */
    private ButtonType resolveButtonType(MouseEvent event) {
        return switch (event.getButton()) {
            case MouseEvent.BUTTON1 -> ButtonType.LEFT_BUTTON;
            case MouseEvent.BUTTON2 -> ButtonType.MIDDLE_BUTTON;
            case MouseEvent.BUTTON3 -> ButtonType.RIGHT_BUTTON;
            default -> throw new IllegalArgumentException("Неизвестная кнопка мыши: " + event.getButton());
        };
    }

    /**
     * Добавляет в компоновщик панель с кнопками клеток.
     *
     * @param buttonsPanel панель с кнопками поля
     */
    private void addButtonsPanel(JPanel buttonsPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.insets = new Insets(20, 20, 5, 20);
        mainLayout.setConstraints(buttonsPanel, gbc);
        contentPane.add(buttonsPanel);
    }

    /**
     * Добавляет иконку таймера в компоновщик.
     */
    private void addTimerImage() {
        JLabel label = new JLabel(GameImage.TIMER.getImageIcon());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        mainLayout.setConstraints(label, gbc);
        contentPane.add(label);
    }

    /**
     * Добавляет метку таймера в компоновщик.
     *
     * @param timerLabel метка таймера
     */
    private void addTimerLabel(JLabel timerLabel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 0, 0);
        mainLayout.setConstraints(timerLabel, gbc);
        contentPane.add(timerLabel);
    }

    /**
     * Добавляет метку счётчика мин в компоновщик.
     *
     * @param bombsCounterLabel метка счётчика мин
     */
    private void addBombCounter(JLabel bombsCounterLabel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0.7;
        mainLayout.setConstraints(bombsCounterLabel, gbc);
        contentPane.add(bombsCounterLabel);
    }

    /**
     * Добавляет иконку бомбы в компоновщик.
     */
    private void addBombCounterImage() {
        JLabel label = new JLabel(GameImage.BOMB_ICON.getImageIcon());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 3;
        gbc.insets = new Insets(0, 5, 0, 20);
        gbc.weightx = 0.1;
        mainLayout.setConstraints(label, gbc);
        contentPane.add(label);
    }
}
