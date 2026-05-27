package com.winter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HallForm extends JFrame {

    JTextField hallNameField;
    JTextField locationField;
    JTextField capacityField;

    JButton saveButton;
    JTable hallTable;

    // 🔐 TOKEN (normally from login)
    static String token = ApiService.token;

    public HallForm() {

        setTitle("Hall Management System");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================= FORM =================
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        hallNameField = new JTextField();
        locationField = new JTextField();
        capacityField = new JTextField();

        saveButton = new JButton("Save Hall");

        formPanel.add(new JLabel("Hall Name:"));
        formPanel.add(hallNameField);

        formPanel.add(new JLabel("Location:"));
        formPanel.add(locationField);

        formPanel.add(new JLabel("Capacity:"));
        formPanel.add(capacityField);

        formPanel.add(new JLabel());
        formPanel.add(saveButton);

        // ================= TABLE =================
        String[] columns = {"ID", "Name", "Location", "Capacity"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        hallTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(hallTable);

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ================= SAVE =================
        saveButton.addActionListener(e -> {

            try {
                String name = hallNameField.getText();
                String location = locationField.getText();
                String capacity = capacityField.getText();

                String response = saveHall(name, location, capacity);

                JOptionPane.showMessageDialog(this, response);

                loadHalls();

                hallNameField.setText("");
                locationField.setText("");
                capacityField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        // load data
        loadHalls();

        setVisible(true);
    }

    // ================= SAVE HALL =================
    private String saveHall(String name, String location, String capacity) {

        try {
            URL url = new URL("http://203.94.72.18/trainee/api/halls");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            // 🔐 TOKEN
            conn.setRequestProperty("Authorization", "Bearer " + token);

            conn.setDoOutput(true);

            String json = "{"
                    + "\"name\":\"" + name.trim() + "\","
                    + "\"location\":\"" + location.trim() + "\","
                    + "\"capacity\":" + Integer.parseInt(capacity.trim())
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            int code = conn.getResponseCode();

            InputStream stream = (code >= 200 && code < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));

            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();

            System.out.println("SAVE RESPONSE: " + sb);

            return sb.toString();

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // ================= LOAD HALLS =================
    private void loadHalls() {

        try {
            URL url = new URL("http://203.94.72.18/trainee/api/halls");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization", "Bearer " + token);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();

            String response = sb.toString();

            DefaultTableModel model = (DefaultTableModel) hallTable.getModel();
            model.setRowCount(0);

            org.json.JSONArray arr;

            if (response.trim().startsWith("[")) {
                arr = new org.json.JSONArray(response);
            } else {
                org.json.JSONObject obj = new org.json.JSONObject(response);

                if (obj.has("data")) {
                    arr = obj.getJSONArray("data");
                } else {
                    throw new Exception("Invalid API response");
                }
            }

            for (int i = 0; i < arr.length(); i++) {

                org.json.JSONObject h = arr.getJSONObject(i);

                model.addRow(new Object[]{
                        h.optInt("id"),
                        h.optString("name"),
                        h.optString("location"),
                        h.optInt("capacity")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new HallForm();
    }
}