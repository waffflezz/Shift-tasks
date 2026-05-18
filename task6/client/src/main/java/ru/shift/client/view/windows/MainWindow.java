package ru.shift.client.view.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWindow {

    private final JFrame frame;

    private JLabel onlineLabel;

    private JTextArea chatArea;
    private JScrollPane chatScroll;

    private JList<String> usersList;
    private DefaultListModel<String> usersModel;

    private JTextField inputField;
    private JButton sendButton;

    public MainWindow() {
        frame = new JFrame("Shift Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(650, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        createTopPanel();
        createCenterPanel();
        createBottomPanel();
    }

    private void createTopPanel() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(new Color(210, 220, 235));

        onlineLabel = new JLabel("Online: 0");

        top.add(onlineLabel);

        frame.add(top, BorderLayout.NORTH);
    }

    private void createCenterPanel() {

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        chatArea.setBackground(new Color(235, 240, 250));

        chatScroll = new JScrollPane(chatArea);

        usersModel = new DefaultListModel<>();
        usersList = new JList<>(usersModel);

        usersList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        usersList.setBackground(new Color(220, 230, 245));
        usersList.setSelectionBackground(new Color(160, 180, 220));
        usersList.setFixedCellHeight(22);

        JScrollPane usersScroll = new JScrollPane(usersList);
        usersScroll.setPreferredSize(new Dimension(160, 0));

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                usersScroll,
                chatScroll
        );

        splitPane.setDividerLocation(160);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.0);

        frame.add(splitPane, BorderLayout.CENTER);
    }

    private void createBottomPanel() {

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(210, 220, 235));

        inputField = new JTextField();
        sendButton = new JButton("Send");

        sendButton.setFocusPainted(false);

        bottom.add(inputField, BorderLayout.CENTER);
        bottom.add(sendButton, BorderLayout.EAST);

        frame.add(bottom, BorderLayout.SOUTH);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void dispose() {
        frame.dispose();
    }

    public void addMessage(String message) {
        chatArea.append(message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    public void setUsers(List<String> users) {
        usersModel.clear();
        for (String user : users) {
            usersModel.addElement(user);
        }
    }

    public void addUser(String username) {
        usersModel.addElement(username);
    }

    public void removeUser(String username) {
        usersModel.removeElement(username);
    }

    public void setOnlineCount(int count) {
        onlineLabel.setText("Online: " + count);
    }

    public void setSendAction(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    public String getInputText() {
        return inputField.getText();
    }

    public void clearInput() {
        inputField.setText("");
    }
}