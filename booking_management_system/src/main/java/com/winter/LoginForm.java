package com.winter;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;

    public LoginForm() {

        setTitle("Hall Booking Login");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(240, 242, 245));
        setLayout(new BorderLayout());

        // ================= TITLE =================
        JLabel title = new JLabel("HALL BOOKING SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        add(title, BorderLayout.NORTH);

        // ================= FORM =================
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(Color.WHITE);

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(new JLabel("Username"));
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password"));
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        // ================= BUTTON =================
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 242, 245));

        loginButton = new JButton("LOGIN");

        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(200, 40));

        loginButton.addActionListener(e -> {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            String response = ApiService.login(username, password);

            // ✅ CLEAN MESSAGE ONLY
            if (response.equals("success")) {

                JOptionPane.showMessageDialog(this, "Login Successful");

                new HallForm();   // open next screen
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Login Failed");
            }
        });

        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        new LoginForm();
    }
}