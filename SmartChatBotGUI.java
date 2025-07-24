package CodeAlpha_ProjectName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChatBotGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    private Map<String, String> knowledgeBase;

    public ChatBotGUI() {
        setTitle("Java ChatBot");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Chat display area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Action listener for send
        sendButton.addActionListener(e -> processInput());
        inputField.addActionListener(e -> processInput());

        // Load chatbot knowledge base
        initializeKnowledgeBase();
    }

    private void processInput() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) return;

        chatArea.append("You: " + input + "\n");
        String response = getBotResponse(input);
        chatArea.append("Bot: " + response + "\n\n");
        inputField.setText("");
    }

    private void initializeKnowledgeBase() {
        knowledgeBase = new HashMap<>();

        // Basic rule-based responses
        knowledgeBase.put("hi", "Hello! How can I help you today?");
        knowledgeBase.put("hello", "Hi there! What would you like to know?");
        knowledgeBase.put("how are you", "I'm just a bot, but I'm doing fine. Thanks for asking!");
        knowledgeBase.put("what is your name", "I'm your Java ChatBot.");
        knowledgeBase.put("bye", "Goodbye! Have a great day.");
        knowledgeBase.put("thank you", "You're welcome!");
        knowledgeBase.put("help", "I can answer your basic questions. Try asking about our services, Java, or programming.");

        // Add more FAQs here
        knowledgeBase.put("what is java", "Java is a high-level, object-oriented programming language.");
        knowledgeBase.put("who developed java", "Java was developed by James Gosling at Sun Microsystems.");
        knowledgeBase.put("what is oops", "OOP stands for Object-Oriented Programming. It includes concepts like class, object, inheritance, polymorphism, etc.");
        knowledgeBase.put("what is swing", "Swing is a part of Java used for building GUI applications.");
        knowledgeBase.put("what is jdk", "JDK stands for Java Development Kit. It is a software development environment for building Java applications.");
    }

    private String getBotResponse(String userInput) {
        String cleanedInput = userInput.toLowerCase().replaceAll("[^a-zA-Z ]", "").trim();

        for (String key : knowledgeBase.keySet()) {
            if (cleanedInput.contains(key)) {
                return knowledgeBase.get(key);
            }
        }

        return "I'm not sure how to respond to that. Try asking something else.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatBotGUI().setVisible(true);
        });
    }
}
