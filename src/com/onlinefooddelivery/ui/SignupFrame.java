package com.onlinefooddelivery.ui;

import com.onlinefooddelivery.dao.UserDAO;
import com.onlinefooddelivery.model.User;

import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {
    private JTextField nameField, emailField, phoneField, addressField;
    private JPasswordField passwordField;
    private UserDAO userDAO = new UserDAO();

    public SignupFrame() {
        super("Signup - Food Delivery");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridLayout(6,2,6,6));
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        passwordField = new JPasswordField();

        p.add(new JLabel("Name:")); p.add(nameField);
        p.add(new JLabel("Email:")); p.add(emailField);
        p.add(new JLabel("Phone:")); p.add(phoneField);
        p.add(new JLabel("Address:")); p.add(addressField);
        p.add(new JLabel("Password:")); p.add(passwordField);

        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> register());

        add(p, BorderLayout.CENTER);
        add(registerBtn, BorderLayout.SOUTH);
    }

    private void register() {
        User u = new User();
        u.setName(nameField.getText().trim());
        u.setEmail(emailField.getText().trim());
        u.setPhone(phoneField.getText().trim());
        u.setAddress(addressField.getText().trim());
        u.setPassword(new String(passwordField.getPassword()));

        boolean ok = userDAO.register(u);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Registration successful. Please login.");
            new LoginFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Try another email.");
        }
    }
}
