package com.ems.view;

import com.ems.util.Session;

import javax.swing.*;
import java.awt.*;

public class EmployeeDashboardFrame extends JFrame {
    
    public EmployeeDashboardFrame() {
        setTitle("EMS Employee Portal - " + Session.currentUser.getFullName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        CardLayout cardLayout = new CardLayout();
        JPanel centerWrapper = new JPanel(cardLayout);
        
        // Modules
        EmployeeProfilePanel profilePanel = new EmployeeProfilePanel();
        EmployeeAttendancePanel attendancePanel = new EmployeeAttendancePanel();
        EmployeeLeavePanel leavePanel = new EmployeeLeavePanel();
        
        centerWrapper.add(profilePanel, "My Profile");
        centerWrapper.add(attendancePanel, "Attendance");
        centerWrapper.add(leavePanel, "Leave Requests");
        
        // Main Content Area
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("My Profile");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        
        contentPanel.add(centerWrapper, BorderLayout.CENTER);

        // Sidebar Navigation
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(45, 52, 54));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        JLabel brandLabel = new JLabel("EMS Portal");
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        brandLabel.setForeground(Color.WHITE);
        brandLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        sidebar.add(brandLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        
        String[] menuItems = {"My Profile", "Attendance", "Leave Requests", "Logout"};
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBackground(new Color(45, 52, 54));
            btn.setForeground(Color.LIGHT_GRAY);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            
            btn.addActionListener(e -> {
                if (item.equals("Logout")) {
                    Session.logout();
                    new WelcomeFrame().setVisible(true);
                    dispose();
                } else {
                    welcomeLabel.setText(item);
                    cardLayout.show(centerWrapper, item);
                    if (item.equals("Leave Requests")) {
                         leavePanel.refreshTable();
                    } else if (item.equals("Attendance")) {
                         attendancePanel.refreshData();
                    }
                }
            });
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
}
