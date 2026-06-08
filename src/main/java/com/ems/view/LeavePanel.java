package com.ems.view;

import com.ems.dao.LeaveDAO;
import com.ems.model.LeaveRequest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LeavePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private LeaveDAO dao;

    public LeavePanel() {
        dao = new LeaveDAO();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Manage Leave Requests");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshTable());
        topPanel.add(refreshBtn, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Employee", "Start Date", "End Date", "Reason", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton approveBtn = new JButton("Approve Leave");
        approveBtn.setBackground(new Color(0, 184, 148));
        approveBtn.setForeground(Color.WHITE);
        approveBtn.addActionListener(e -> updateSelectedLeave("APPROVED"));

        JButton rejectBtn = new JButton("Reject Leave");
        rejectBtn.setBackground(new Color(225, 112, 85));
        rejectBtn.setForeground(Color.WHITE);
        rejectBtn.addActionListener(e -> updateSelectedLeave("REJECTED"));

        bottomPanel.add(rejectBtn);
        bottomPanel.add(approveBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<LeaveRequest> requests = dao.getAllLeaveRequests();
        for (LeaveRequest req : requests) {
            tableModel.addRow(new Object[]{
                req.getId(),
                req.getEmployeeName(),
                req.getStartDate(),
                req.getEndDate(),
                req.getReason(),
                req.getStatus()
            });
        }
    }

    private void updateSelectedLeave(String status) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            if (dao.updateLeaveStatus(id, status)) {
                JOptionPane.showMessageDialog(this, "Leave request marked as " + status + "!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update record.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a leave request from the table first.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
