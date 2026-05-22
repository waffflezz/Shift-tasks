package ru.shift.client.view.windows;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.client.view.views.handlers.MessageHandler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class MainWindow {
    private static final int MAX_CHARS = 100;

    private final JFrame frame;
    private JLabel onlineLabel;
    private JTextPane chatArea;
    private JScrollPane chatScroll;
    private JList<String> usersList;
    private DefaultListModel<String> usersModel;
    private JTextField inputField;
    private JButton sendButton;
    private JLabel charCountLabel;

    @Setter
    private MessageHandler messageHandler;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    private final Color systemColor = new Color(100, 100, 100);
    private final Color userColor = new Color(0, 0, 0);
    private final Color timeColor = new Color(150, 150, 150);
    private final Color systemBgColor = new Color(240, 245, 255);

    public MainWindow() {
        frame = new JFrame("Shift Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(650, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        createTopPanel();
        createCenterPanel();
        createBottomPanel();

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCharCount();
                limitInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCharCount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCharCount();
            }
        });
    }

    public void addUserMessage(String username, String message, Instant time) {
        String timeStr = timeFormatter.format(time);
        appendStyledMessage(username, message, timeStr, userColor, Color.WHITE, false);
    }

    public void addSystemMessage(String message, Instant time) {
        String timeStr = timeFormatter.format(time);
        appendStyledMessage(null, message, timeStr, systemColor, systemBgColor, true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void dispose() {
        frame.dispose();
    }

    public void setUsers(List<String> users) {
        usersModel.clear();
        for (String user : users) {
            usersModel.addElement(user);
        }
        updateOnlineCount();
    }

    public void addUser(String username) {
        if (!usersModel.contains(username)) {
            usersModel.addElement(username);
            updateOnlineCount();
        }
    }

    public void removeUser(String username) {
        usersModel.removeElement(username);
        updateOnlineCount();
    }

    public String getInputText() {
        return inputField.getText();
    }

    public void clearInput() {
        inputField.setText("");
    }

    public void setOnlineCount(int count) {
        onlineLabel.setText("Online: " + count);
    }

    private void updateCharCount() {
        int length = inputField.getText().length();
        charCountLabel.setText(length + "/" + MAX_CHARS);

        // Меняем цвет при приближении к лимиту
        if (length >= MAX_CHARS) {
            charCountLabel.setForeground(Color.RED);
        } else if (length >= MAX_CHARS * 0.8) {
            charCountLabel.setForeground(Color.ORANGE);
        } else {
            charCountLabel.setForeground(new Color(120, 120, 120));
        }
    }

    private void limitInput() {
        if (inputField.getText().length() > MAX_CHARS) {
            inputField.setText(inputField.getText().substring(0, MAX_CHARS));
        }
    }

    private void createTopPanel() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(new Color(210, 220, 235));

        onlineLabel = new JLabel("Online: 0");
        top.add(onlineLabel);
        frame.add(top, BorderLayout.NORTH);
    }

    private void createCenterPanel() {
        chatArea = new JTextPane();
        chatArea.setEditable(false);
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

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(210, 220, 235));

        inputField = new JTextField();
        inputField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) {
                if (str == null) return;
                if (getLength() + str.length() <= MAX_CHARS) {
                    try {
                        super.insertString(offs, str, a);
                    } catch (BadLocationException e) {
                        log.warn("Can't insert string. Error: {}", e.getMessage());
                    }
                }
            }
        });

        charCountLabel = new JLabel("0/" + MAX_CHARS);
        charCountLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
        charCountLabel.setForeground(new Color(120, 120, 120));
        charCountLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(charCountLabel, BorderLayout.EAST);

        sendButton = new JButton("Send");
        sendButton.setFocusPainted(false);

        bottom.add(inputPanel, BorderLayout.CENTER);
        bottom.add(sendButton, BorderLayout.EAST);

        frame.add(bottom, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String text = inputField.getText().trim();

        if (text.isEmpty()) {
            return;
        }

        if (messageHandler != null) {
            messageHandler.handle(text);
        }
    }

    private void appendStyledMessage(String username, String message, String timeStr,
                                     Color textColor, Color bgColor, boolean isSystem) {
        StyledDocument doc = chatArea.getStyledDocument();

        SimpleAttributeSet timeStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(timeStyle, timeColor);
        StyleConstants.setFontSize(timeStyle, 11);
        StyleConstants.setBold(timeStyle, false);

        SimpleAttributeSet userStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(userStyle, new Color(0, 100, 200));
        StyleConstants.setFontSize(userStyle, 13);
        StyleConstants.setBold(userStyle, true);

        SimpleAttributeSet messageStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(messageStyle, textColor);
        StyleConstants.setFontSize(messageStyle, 13);
        StyleConstants.setBold(messageStyle, isSystem);

        if (isSystem) {
            StyleConstants.setItalic(messageStyle, true);
        }

        SimpleAttributeSet bgStyle = new SimpleAttributeSet();
        StyleConstants.setBackground(bgStyle, bgColor);

        try {
            doc.insertString(doc.getLength(), "[" + timeStr + "] ", timeStyle);

            if (isSystem) {
                SimpleAttributeSet starStyle = new SimpleAttributeSet();
                StyleConstants.setForeground(starStyle, systemColor);
                StyleConstants.setFontSize(starStyle, 13);
                StyleConstants.setBold(starStyle, true);

                doc.insertString(doc.getLength(), "*** ", starStyle);
                doc.insertString(doc.getLength(), message + " ", messageStyle);
                doc.insertString(doc.getLength(), "***", starStyle);
            } else {
                doc.insertString(doc.getLength(), username + ": ", userStyle);
                doc.insertString(doc.getLength(), message, messageStyle);
            }

            doc.insertString(doc.getLength(), "\n", new SimpleAttributeSet());

            autoScrollToBottom();
        } catch (BadLocationException e) {
            log.error("Error with Swing, when style message. Error: {}", e.getMessage());
        }
    }

    private void autoScrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            if (chatScroll.isDisplayable() && chatScroll.isVisible()) {
                JScrollBar vertical = chatScroll.getVerticalScrollBar();
                if (vertical != null && vertical.isVisible()) {
                    vertical.setValue(vertical.getMaximum());
                }
            }
        });
    }

    private void updateOnlineCount() {
        onlineLabel.setText("Online: " + usersModel.size());
    }
}