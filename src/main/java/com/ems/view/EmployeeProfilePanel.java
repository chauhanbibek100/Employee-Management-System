package com.ems.view;

import com.ems.util.Session;
import javax.swing.*;
import java.awt.*;

public class EmployeeProfilePanel extends JPanel {

    public EmployeeProfilePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Your Profile Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 20, 20));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        detailsPanel.setMaximumSize(new Dimension(500, 300));
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 16);

        detailsPanel.add(createLabel("Full Name:", labelFont));
        detailsPanel.add(createLabel(Session.currentUser.getFullName(), valueFont));

        detailsPanel.add(createLabel("Email:", labelFont));
        detailsPanel.add(createLabel(Session.currentUser.getEmail(), valueFont));

        detailsPanel.add(createLabel("Phone:", labelFont));
        detailsPanel.add(createLabel(Session.currentUser.getPhone(), valueFont));

        detailsPanel.add(createLabel("Department:", labelFont));
        detailsPanel.add(createLabel(Session.currentUser.getDepartment(), valueFont));

        detailsPanel.add(createLabel("Role:", labelFont));
        detailsPanel.add(createLabel(Session.currentUser.getRole(), valueFont));

        detailsPanel.add(createLabel("Salary:", labelFont));
        detailsPanel.add(createLabel("₹" + Session.currentUser.getSalary(), valueFont));

        mainContent.add(titleLabel);
        mainContent.add(detailsPanel);
        
        add(mainContent, BorderLayout.NORTH);
    }
    
    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        return lbl;
    }
}
