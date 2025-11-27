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
        super("Sign in — Food Delivery");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 380);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.SURFACE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIUtils.SURFACE);
        root.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Welcome back");
        title.setFont(UIUtils.HEADLINE);
        title.setForeground(UIUtils.PRIMARY_DARK);

        JPanel center = new JPanel();
        center.setBackground(UIUtils.SURFACE);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));

        center.add(title);
        center.add(Box.createRigidArea(new Dimension(0,18)));

        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE,36));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(6,8,6,8)
        ));
        emailField.setToolTipText("Email");

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE,36));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(6,8,6,8)
        ));
        passwordField.setToolTipText("Password");

        center.add(new JLabel("Email"));
        center.add(emailField);
        center.add(Box.createRigidArea(new Dimension(0,10)));
        center.add(new JLabel("Password"));
        center.add(passwordField);
        center.add(Box.createRigidArea(new Dimension(0,18)));

        JButton loginBtn = UIUtils.createPrimaryButton("Sign in");
        loginBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginBtn.addActionListener(e -> doLogin());

        JButton signupBtn = new JButton("Create account");
        signupBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        signupBtn.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        signupBtn.addActionListener(e -> {
            new SignupFrame().setVisible(true);
            dispose();
        });

        center.add(loginBtn);
        center.add(Box.createRigidArea(new Dimension(0,8)));
        center.add(signupBtn);

        root.add(center, BorderLayout.CENTER);
        getContentPane().add(root);
    }

    private void doLogin() {
        String email = emailField.getText().trim();
        String pass = new String(passwordField.getPassword());
        try {
            User user = userDAO.login(email, pass);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Welcome, " + user.getName());
                new RestaurantListFrame(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}