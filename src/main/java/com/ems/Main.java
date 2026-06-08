package com.ems;

import com.formdev.flatlaf.FlatLightLaf;
import com.ems.view.WelcomeFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;

public class Main {
    public static void main(String[] args) {
        // Setup FlatLaf for a modern look
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            // Global styling for rounded corners
            UIManager.put("Button.arc", 15);
            UIManager.put("Component.arc", 15);
            UIManager.put("TextComponent.arc", 15);
            
            // Set a default global font
            UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // Launch app
        SwingUtilities.invokeLater(() -> {
            new WelcomeFrame().setVisible(true);
        });
    }
}
