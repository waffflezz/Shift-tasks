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

public class MainWindow {
    private final JFrame frame;
    private final Container contentPane;
    private final GridBagLayout mainLayout;

    private JMenuItem newGameMenu;
    private JMenuItem highScoresMenu;
    private JMenuItem settingsMenu;

    @Setter
    private CellClickHandler cellClickHandler;
    private JButton[][] cellButtons;
    private JLabel timerLabel;
    private JLabel bombsCounterLabel;

    public MainWindow() {
        frame = new JFrame("Miner");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        createMenu();

        contentPane = frame.getContentPane();
        mainLayout = new GridBagLayout();
        contentPane.setLayout(mainLayout);
        contentPane.setBackground(new Color(144, 158, 184));
    }

    public Window getWindow() {
        return frame;
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void dispose() {
        frame.dispose();
    }

    public void setNewGameMenuAction(ActionListener listener) {
        newGameMenu.addActionListener(listener);
    }

    public void setHighScoresMenuAction(ActionListener listener) {
        highScoresMenu.addActionListener(listener);
    }

    public void setSettingsMenuAction(ActionListener listener) {
        settingsMenu.addActionListener(listener);
    }

    public void setCellImage(int x, int y, GameImage gameImage) {
        cellButtons[y][x].setIcon(gameImage.getImageIcon());
    }

    public void setBombsCount(int bombsCount) {
        bombsCounterLabel.setText(String.valueOf(bombsCount));
    }

    public void setTimerValue(int value) {
        timerLabel.setText(String.valueOf(value));
    }

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

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        gameMenu.add(newGameMenu = new JMenuItem("New Game"));
        gameMenu.addSeparator();
        gameMenu.add(highScoresMenu = new JMenuItem("High Scores"));
        gameMenu.add(settingsMenu = new JMenuItem("Settings"));
        gameMenu.addSeparator();

        JMenuItem exitMenu = new JMenuItem("Exit");
        gameMenu.add(exitMenu);
        exitMenu.addActionListener(e -> dispose());

        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);
    }

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

    private void handleCellClick(int x, int y, MouseEvent event) {
        if (cellClickHandler == null) {
            return;
        }

        cellClickHandler.onCellClick(x, y, resolveButtonType(event));
    }

    private ButtonType resolveButtonType(MouseEvent event) {
        return switch (event.getButton()) {
            case MouseEvent.BUTTON1 -> ButtonType.LEFT_BUTTON;
            case MouseEvent.BUTTON2 -> ButtonType.MIDDLE_BUTTON;
            case MouseEvent.BUTTON3 -> ButtonType.RIGHT_BUTTON;
            default -> throw new IllegalArgumentException("Неизвестная кнопка мыши: " + event.getButton());
        };
    }

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

    private void addTimerLabel(JLabel timerLabel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 0, 0);
        mainLayout.setConstraints(timerLabel, gbc);
        contentPane.add(timerLabel);
    }

    private void addBombCounter(JLabel bombsCounterLabel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0.7;
        mainLayout.setConstraints(bombsCounterLabel, gbc);
        contentPane.add(bombsCounterLabel);
    }

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
