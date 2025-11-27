package com.onlinefooddelivery.main;

import com.onlinefooddelivery.ui.LoginFrame;
import com.onlinefooddelivery.ui.UIUtils;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UIUtils.applyMaterialTheme();
            LoginFrame f = new LoginFrame();
            f.setVisible(true);
        });
    }
}