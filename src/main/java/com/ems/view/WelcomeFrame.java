package com.ems.view;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame() {
        setTitle("EMS - Welcome");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 60, 50));

        JLabel titleLabel = new JLabel("Welcome to EMS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Please select your portal to continue");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JButton adminBtn = new JButton("Admin Portal");
        adminBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        adminBtn.setBackground(new Color(9, 132, 227));
        adminBtn.setForeground(Color.WHITE);
        adminBtn.setFocusPainted(false);
        adminBtn.setMaximumSize(new Dimension(300, 60));
        adminBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminBtn.addActionListener(e -> {
            new LoginFrame("ADMIN").setVisible(true);
            this.dispose();
        });

        JButton empBtn = new JButton("Employee Portal");
        empBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        empBtn.setBackground(new Color(0, 184, 148));
        empBtn.setForeground(Color.WHITE);
        empBtn.setFocusPainted(false);
        empBtn.setMaximumSize(new Dimension(300, 60));
        empBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        empBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        empBtn.addActionListener(e -> {
            new LoginFrame("EMPLOYEE").setVisible(true);
            this.dispose();
        });

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(adminBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(empBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }
}
