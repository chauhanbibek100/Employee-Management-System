package com.ems.view;

import com.ems.dao.EmployeeDAO;
import com.ems.model.Employee;
import com.ems.util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private String expectedRole;

    public LoginFrame(String expectedRole) {
        this.expectedRole = expectedRole;
        setTitle(expectedRole.equals("ADMIN") ? "EMS - Admin Login" : "EMS - Employee Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        setLayout(new BorderLayout());
        
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 40, 50, 40));

        // Logo / Title
        JLabel titleLabel = new JLabel(expectedRole.equals("ADMIN") ? "Admin Portal" : "Employee Portal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Login to continue");
        subtitleLabel.setFont(UIManager.getFont("h3.font"));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Fields
        emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.putClientProperty("JTextField.placeholderText", "Email");

        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.putClientProperty("JTextField.placeholderText", "Password");

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            new WelcomeFrame().setVisible(true);
            this.dispose();
        });

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 120, 215)); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        loginButton.addActionListener(this::handleLogin);

        buttonPanel.add(backButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(loginButton);

        // Add components with spacing
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        mainPanel.add(new JLabel("Email Address"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(emailField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(new JLabel("Password"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);
        
        // Rootpane default button (enter key triggers login)
        getRootPane().setDefaultButton(loginButton);
    }

    private void handleLogin(ActionEvent e) {
        String email = emailField.getText();
        String pwd = new String(passwordField.getPassword());
        
        if (email.isEmpty() || pwd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EmployeeDAO dao = new EmployeeDAO();
        Employee emp = dao.login(email, pwd);
        
        if (emp != null) {
            // Verify RBAC access
            if (!emp.getRole().equalsIgnoreCase(expectedRole)) {
                JOptionPane.showMessageDialog(this, 
                    "Access Denied: You do not have permission to access the " + expectedRole + " portal. You must login via the " + emp.getRole() + " portal.", 
                    "Permission Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Session.currentUser = emp;
            
            // Route
            if (emp.getRole().equalsIgnoreCase("ADMIN")) {
                new DashboardFrame().setVisible(true);
            } else {
                new EmployeeDashboardFrame().setVisible(true);
            }
            this.dispose(); // Close login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
