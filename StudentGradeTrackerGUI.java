package CodeAlpha_ProjectName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    int rollNo;
    double score;

    public Student(String name, int rollNo, double score) {
        this.name = name;
        this.rollNo = rollNo;
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("Name: %s | Roll No: %d | Score: %.2f", name, rollNo, score);
    }
}

public class StudentGradeManager extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();

    private JTextField nameField = new JTextField(15);
    private JTextField rollField = new JTextField(5);
    private JTextField scoreField = new JTextField(5);
    private JTextArea outputArea = new JTextArea(10, 40);
    private JButton addButton = new JButton("Add Student");
    private JButton showAllButton = new JButton("Show All Students");
    private JButton summaryButton = new JButton("Show Summary");

    public StudentGradeManager() {
        setTitle("Student Grade Manager - GUI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Top Panel - Input
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Roll No:"));
        inputPanel.add(rollField);
        inputPanel.add(new JLabel("Score:"));
        inputPanel.add(scoreField);
        inputPanel.add(addButton);

        // Center - Output
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Bottom Panel - Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showAllButton);
        buttonPanel.add(summaryButton);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> addStudent());
        showAllButton.addActionListener(e -> showAllStudents());
        summaryButton.addActionListener(e -> showSummary());
    }

    private void addStudent() {
        try {
            String name = nameField.getText().trim();
            int rollNo = Integer.parseInt(rollField.getText().trim());
            double score = Double.parseDouble(scoreField.getText().trim());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name.");
                return;
            }

            students.add(new Student(name, rollNo, score));
            outputArea.append("Student added: " + name + "\n");

            // Clear fields
            nameField.setText("");
            rollField.setText("");
            scoreField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.");
        }
    }

    private void showAllStudents() {
        outputArea.setText("--- All Students ---\n");
        if (students.isEmpty()) {
            outputArea.append("No students added.\n");
            return;
        }

        for (Student s : students) {
            outputArea.append(s.toString() + "\n");
        }
    }

    private void showSummary() {
        if (students.isEmpty()) {
            outputArea.setText("No students to summarize.\n");
            return;
        }

        double total = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;

        for (Student s : students) {
            total += s.score;
            if (s.score > max) max = s.score;
            if (s.score < min) min = s.score;
        }

        double average = total / students.size();

        outputArea.setText("--- Summary Report ---\n");
        outputArea.append("Total Students: " + students.size() + "\n");
        outputArea.append("Average Score: " + String.format("%.2f", average) + "\n");
        outputArea.append("Highest Score: " + String.format("%.2f", max) + "\n");
        outputArea.append("Lowest Score: " + String.format("%.2f", min) + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeManager().setVisible(true);
        });
    }
}

