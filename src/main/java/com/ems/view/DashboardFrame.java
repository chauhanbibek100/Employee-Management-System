package com.ems.view;

import com.ems.dao.EmployeeDAO;
import com.ems.util.Session;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    
    private JLabel totalEmployeesLabel;
    private JLabel activeLeavesLabel;
    
    public DashboardFrame() {
        setTitle("EMS Dashboard - Logged in as: " + (Session.currentUser != null ? Session.currentUser.getFullName() : "Admin"));
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        // Central CardLayout for switching views
        CardLayout cardLayout = new CardLayout();
        JPanel centerWrapper = new JPanel(cardLayout);
        
        // Modules
        EmployeePanel employeePanel = new EmployeePanel();
        AttendancePanel attendancePanel = new AttendancePanel();
        LeavePanel leavePanel = new LeavePanel();
        
        // Home Overview Panel
        JPanel overviewPanel = new JPanel(new BorderLayout());
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        
        // Initialize dynamic labels
        totalEmployeesLabel = new JLabel("0");
        activeLeavesLabel = new JLabel("0");
        
        statsPanel.add(createStatCard("Total Employees", totalEmployeesLabel, new Color(9, 132, 227)));
        statsPanel.add(createStatCard("Departments", new JLabel("IT, HR, Mgt"), new Color(0, 184, 148)));
        statsPanel.add(createStatCard("Active Leaves", activeLeavesLabel, new Color(225, 112, 85)));
        overviewPanel.add(statsPanel, BorderLayout.NORTH);
        
        JLabel placeholder = new JLabel("Welcome to the Home Dashboard.");
        placeholder.setHorizontalAlignment(SwingConstants.CENTER);
        placeholder.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        placeholder.setForeground(Color.GRAY);
        overviewPanel.add(placeholder, BorderLayout.CENTER);
        
        centerWrapper.add(overviewPanel, "Dashboard");
        centerWrapper.add(employeePanel, "Manage Employees");
        centerWrapper.add(attendancePanel, "Attendance");
        centerWrapper.add(leavePanel, "Leave Requests");
        
        // Main Content Area
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Dashboard Overview");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Add padding to content area
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
        
        String[] menuItems = {"Dashboard", "Manage Employees", "Attendance", "Leave Requests", "Logout"};
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
                    welcomeLabel.setText(item + " Overview");
                    cardLayout.show(centerWrapper, item);
                    
                    // Trigger refreshes when entering a panel
                    if (item.equals("Dashboard")) {
                        refreshStats();
                    } else if (item.equals("Attendance")) {
                        attendancePanel.refreshTable();
                    } else if (item.equals("Leave Requests")) {
                        leavePanel.refreshTable();
                    }
                }
            });
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        
        // Initial fetch
        refreshStats();
    }
    
    private void refreshStats() {
        // Fetch new data from database
        int total = new EmployeeDAO().getTotalEmployees();
        // Assuming leave requests count can be approximated by size of list for now, 
        // to save time instead of creating a specific count query.
        int activeLeaves = new com.ems.dao.LeaveDAO().getAllLeaveRequests().size();
        
        totalEmployeesLabel.setText(String.valueOf(total));
        activeLeavesLabel.setText(String.valueOf(activeLeaves));
    }
    
    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(new Color(255, 255, 255, 200));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(valueLabel);
        
        return card;
    }
}
