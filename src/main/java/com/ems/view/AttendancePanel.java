package com.ems.view;

import com.ems.dao.AttendanceDAO;
import com.ems.model.Attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AttendancePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private AttendanceDAO dao;

    public AttendancePanel() {
        dao = new AttendanceDAO();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Employee Attendance Logs");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshTable());
        topPanel.add(refreshBtn, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Log ID", "Employee", "Check-In", "Check-Out", "Status"};
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

        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Attendance> logs = dao.getAllAttendances();
        for (Attendance att : logs) {
            tableModel.addRow(new Object[]{
                att.getId(),
                att.getEmployeeName(),
                att.getCheckIn() != null ? att.getCheckIn().toString() : "N/A",
                att.getCheckOut() != null ? att.getCheckOut().toString() : "Not Checked Out",
                att.getStatus()
            });
        }
    }
}
