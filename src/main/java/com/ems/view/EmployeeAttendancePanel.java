package com.ems.view;

import com.ems.dao.AttendanceDAO;
import com.ems.model.Attendance;
import com.ems.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeAttendancePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private AttendanceDAO dao;

    public EmployeeAttendancePanel() {
        dao = new AttendanceDAO();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("My Attendance Logs");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton clockInBtn = new JButton("Clock-In Now");
        clockInBtn.setBackground(new Color(0, 184, 148));
        clockInBtn.setForeground(Color.WHITE);
        clockInBtn.addActionListener(e -> {
            boolean success = dao.clockIn(Session.currentUser.getId());
            if (success) {
                JOptionPane.showMessageDialog(this, "Successfully Clocked In at " + new java.sql.Timestamp(System.currentTimeMillis()));
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to clock in.");
            }
        });

        JButton clockOutBtn = new JButton("Clock-Out Now");
        clockOutBtn.setBackground(new Color(225, 112, 85));
        clockOutBtn.setForeground(Color.WHITE);
        clockOutBtn.addActionListener(e -> {
            boolean success = dao.clockOut(Session.currentUser.getId());
            if (success) {
                JOptionPane.showMessageDialog(this, "Successfully Clocked Out at " + new java.sql.Timestamp(System.currentTimeMillis()));
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to clock out. Did you forget to clock in first?");
            }
        });

        actionPanel.add(clockInBtn);
        actionPanel.add(clockOutBtn);
        topPanel.add(actionPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Log ID", "Check-In Time", "Check-Out Time", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);

        refreshData();
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Attendance> logs = dao.getAttendanceByEmployee(Session.currentUser.getId());
        for (Attendance att : logs) {
            tableModel.addRow(new Object[]{
                att.getId(),
                att.getCheckIn() != null ? att.getCheckIn().toString() : "N/A",
                att.getCheckOut() != null ? att.getCheckOut().toString() : "Not Checked Out",
                att.getStatus()
            });
        }
    }
}
