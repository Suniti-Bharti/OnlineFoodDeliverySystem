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
        super("Create account — Food Delivery");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(480, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.SURFACE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIUtils.SURFACE);
        root.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Create your account");
        title.setFont(UIUtils.HEADLINE);
        title.setForeground(UIUtils.PRIMARY_DARK);

        JPanel center = new JPanel();
        center.setBackground(UIUtils.SURFACE);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        nameField = new JTextField(); emailField = new JTextField();
        phoneField = new JTextField(); addressField = new JTextField();
        passwordField = new JPasswordField();

        for (JComponent c : new JComponent[]{nameField, emailField, phoneField, addressField, passwordField}) {
            c.setMaximumSize(new Dimension(Integer.MAX_VALUE,36));
            c.setAlignmentX(Component.LEFT_ALIGNMENT);
            c.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220,220,220)),
                    BorderFactory.createEmptyBorder(6,8,6,8)
            ));
        }

        center.add(title); center.add(Box.createRigidArea(new Dimension(0,12)));
        center.add(new JLabel("Name")); center.add(nameField);
        center.add(new JLabel("Email")); center.add(emailField);
        center.add(new JLabel("Phone")); center.add(phoneField);
        center.add(new JLabel("Address")); center.add(addressField);
        center.add(new JLabel("Password")); center.add(passwordField);
        center.add(Box.createRigidArea(new Dimension(0,12)));

        JButton createBtn = UIUtils.createPrimaryButton("Create account");
        createBtn.addActionListener(e -> doSignup());
        center.add(createBtn);

        JButton back = new JButton("Back to login");
        back.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        center.add(Box.createRigidArea(new Dimension(0,8)));
        center.add(back);

        root.add(center, BorderLayout.CENTER);
        getContentPane().add(root);
    }

    private void doSignup() {
        User u = new User();
        u.setName(nameField.getText().trim());
        u.setEmail(emailField.getText().trim());
        u.setPhone(phoneField.getText().trim());
        u.setAddress(addressField.getText().trim());
        u.setPassword(new String(passwordField.getPassword()));
        try {
            boolean ok = userDAO.register(u);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Account created. Please login.");
                new LoginFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create account.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: "+ex.getMessage());
        }
    }
}