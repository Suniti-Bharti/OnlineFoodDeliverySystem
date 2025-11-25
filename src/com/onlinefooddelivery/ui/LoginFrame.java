package com.onlinefooddelivery.ui;

import com.onlinefooddelivery.dao.UserDAO;
import com.onlinefooddelivery.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        super("Login - Food Delivery");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridLayout(4,1,10,10));
        emailField = new JTextField();
        passwordField = new JPasswordField();

        p.add(new JLabel("Email:"));
        p.add(emailField);
        p.add(new JLabel("Password:"));
        p.add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Signup");

        loginBtn.addActionListener(e -> doLogin());
        signupBtn.addActionListener(e -> {
            new SignupFrame().setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(loginBtn);
        bottom.add(signupBtn);

        add(p, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void doLogin() {
        String email = emailField.getText().trim();
        String pass = new String(passwordField.getPassword());

        User user = userDAO.login(email, pass);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Welcome, " + user.getName());
            new RestaurantListFrame(user).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
            System.out.println("Attempt login: email='" + email + "' password='" + pass + "'");

        }
    }
}
