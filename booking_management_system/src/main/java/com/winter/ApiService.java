package com.winter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {

    // 🔐 TOKEN STORED SILENTLY (NOT SHOWN TO USER)
    public static String token = "";

    // ================= LOGIN =================
    public static String login(String username, String password) {

        try {
            URL url = new URL("http://203.94.72.18/trainee/api/auth/signin");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = "{"
                    + "\"username\":\"" + username + "\","
                    + "\"password\":\"" + password + "\""
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();

            String response = sb.toString();

            // 🔐 STORE TOKEN SILENTLY
            org.json.JSONObject obj = new org.json.JSONObject(response);
            token = obj.getString("token");

            return "success";

        } catch (Exception e) {
            return "error";
        }
    }

    // ================= SAVE HALL =================
    public static String saveHall(String name, String location, String capacity) {

        try {
            URL url = new URL("http://203.94.72.18/trainee/api/halls");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            // 🔐 TOKEN ADDED
            conn.setRequestProperty("Authorization", "Bearer " + token);

            conn.setDoOutput(true);

            String json = "{"
                    + "\"name\":\"" + name + "\","
                    + "\"location\":\"" + location + "\","
                    + "\"capacity\":" + capacity
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();

            return sb.toString();

        } catch (Exception e) {
            return e.toString();
        }
    }

    // ================= GET HALLS =================
    public static String getHalls() {

        try {
            URL url = new URL("http://203.94.72.18/trainee/api/halls");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 🔐 TOKEN ADDED
            conn.setRequestProperty("Authorization", "Bearer " + token);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();

            return sb.toString();

        } catch (Exception e) {
            return e.toString();
        }
    }
}