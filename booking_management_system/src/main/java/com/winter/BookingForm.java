package com.winter;

import javax.swing.*;
import java.awt.*;

public class BookingForm extends JFrame {

    JTextField dateField;
    JTextField startTimeField;
    JTextField endTimeField;
    JTextField bookingForField;
    JTextField hallIdField;
    JTextField userIdField;

    JButton checkButton;
    JButton bookButton;

    public BookingForm() {

        setTitle("Hall Booking System");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 247));

        // ================= TITLE =================
        JLabel title = new JLabel("HALL BOOKING");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        add(title, BorderLayout.NORTH);

        // ================= FORM PANEL =================
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Row 1 - Date
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Reserved Date"), gbc);

        dateField = new JTextField();
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        // Row 2 - Booking For
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Booking For"), gbc);

        bookingForField = new JTextField();
        gbc.gridx = 1;
        formPanel.add(bookingForField, gbc);

        // Row 3 - Start Time
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Start Time"), gbc);

        startTimeField = new JTextField();
        gbc.gridx = 1;
        formPanel.add(startTimeField, gbc);

        // Row 4 - End Time
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("End Time"), gbc);

        endTimeField = new JTextField();
        gbc.gridx = 1;
        formPanel.add(endTimeField, gbc);

        // Row 5 - Hall ID
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Hall ID"), gbc);

        hallIdField = new JTextField();
        gbc.gridx = 1;
        formPanel.add(hallIdField, gbc);

        // Row 6 - User ID
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("User ID"), gbc);

        userIdField = new JTextField();
        gbc.gridx = 1;
        formPanel.add(userIdField, gbc);

        // ================= BUTTONS =================
        checkButton = new JButton("CHECK AVAILABILITY");
        bookButton = new JButton("BOOK HALL");

        styleButton(checkButton, new Color(245, 158, 11)); // orange
        styleButton(bookButton, new Color(16, 185, 129));  // green

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        btnPanel.add(checkButton);
        btnPanel.add(bookButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;

        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ================= ACTIONS =================
        checkButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Checking availability...");
        });

        bookButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Hall booked successfully!");
        });

        setVisible(true);
    }

    // reusable button style method
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        new BookingForm();
    }
}