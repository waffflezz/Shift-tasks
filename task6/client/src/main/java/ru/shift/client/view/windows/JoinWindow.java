package ru.shift.client.view.windows;

import lombok.Setter;
import ru.shift.client.view.views.handlers.ConnectionHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Окно подключения к серверу.
 * Содержит поля ввода IP-адреса и порта, а также кнопку подключения.
 */
public class JoinWindow {
    private final JFrame frame;

    private JTextField ipField;
    private JTextField portField;
    private JButton connectButton;

    private JLabel errorLabel;

    @Setter
    private ConnectionHandler connectionHandler;

    public JoinWindow() {
        frame = new JFrame("Connect");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 180);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        createForm();

        connectButton.addActionListener(e -> handleConnection());
        ipField.addActionListener(e -> portField.requestFocusInWindow());
        portField.addActionListener(e -> handleConnection());
    }

    private void handleConnection() {
        if (connectionHandler != null) {
            connectionHandler.handle(getIp(), getPort());
        }
    }

    private void createForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(210, 220, 235));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel ipLabel = new JLabel("IP:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(ipLabel, gbc);

        ipField = new JTextField("127.0.0.1");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panel.add(ipField, gbc);

        JLabel portLabel = new JLabel("Port:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(portLabel, gbc);

        portField = new JTextField("8080");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panel.add(portField, gbc);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        panel.add(errorLabel, gbc);

        connectButton = new JButton("Connect");
        connectButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        panel.add(connectButton, gbc);

        frame.add(panel, BorderLayout.CENTER);
    }

    public void showError(String error) {
        errorLabel.setText("Ошибка: " + error);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void dispose() {
        frame.dispose();
    }

    public String getIp() {
        return ipField.getText();
    }

    public int getPort() {
        return Integer.parseInt(portField.getText());
    }
}