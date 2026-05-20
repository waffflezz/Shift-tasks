package ru.shift.client.view.windows;

import lombok.Setter;
import ru.shift.client.view.views.handlers.AuthHandler;

import javax.swing.*;
import java.awt.*;

public class AuthWindow {
    private final JFrame frame;

    private JTextField nicknameField;
    private JButton loginButton;

    private JLabel errorLabel;

    @Setter
    private AuthHandler authHandler;

    public AuthWindow() {
        frame = new JFrame("Authorization");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 160);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        createForm();

        loginButton.addActionListener(e -> {
            if (authHandler != null) {
                authHandler.handle(getNickname());
            }
        });
    }

    private void createForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(210, 220, 235));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nicknameLabel = new JLabel("Nickname:");

        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(nicknameLabel, gbc);

        nicknameField = new JTextField();

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;

        panel.add(nicknameField, gbc);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        panel.add(errorLabel, gbc);

        loginButton = new JButton("Login");
        loginButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        panel.add(loginButton, gbc);

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

    public String getNickname() {
        return nicknameField.getText();
    }
}