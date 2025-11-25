

package com.onlinefooddelivery.ui;

import javax.swing.*;
        import java.awt.*;

public class AdminPanelFrame extends JFrame {
    public AdminPanelFrame() {
        super("Admin Panel"); // identify error
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel p = new JPanel(new GridLayout(1, 3));
        p.add(new JLabel("Add Restaurant (TODO)")); // fixed missing parenthesis
        // For brevity, this can be extended to allow admin CRUD
        add(p);

        setVisible(true); // important to display the frame
    }
}
