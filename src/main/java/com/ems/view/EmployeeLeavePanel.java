package com.ems.view;

import com.ems.dao.LeaveDAO;
import com.ems.model.LeaveRequest;
import com.ems.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeeLeavePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private LeaveDAO dao;

    public EmployeeLeavePanel() {
        dao = new LeaveDAO();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("My Leave Requests");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton requestBtn = new JButton("Request Leave");
        requestBtn.setBackground(new Color(9, 132, 227));
        requestBtn.setForeground(Color.WHITE);
        requestBtn.addActionListener(e -> {
            new LeaveRequestDialog((JFrame) SwingUtilities.getWindowAncestor(this)).setVisible(true);
            refreshTable();
        });

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshTable());

        actionPanel.add(refreshBtn);
        actionPanel.add(requestBtn);
        topPanel.add(actionPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Start Date", "End Date", "Reason", "Status"};
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
        List<LeaveRequest> requests = dao.getLeaveRequestsByEmployee(Session.currentUser.getId());
        for (LeaveRequest req : requests) {
            tableModel.addRow(new Object[]{
                req.getId(),
                req.getStartDate(),
                req.getEndDate(),
                req.getReason(),
                req.getStatus()
            });
        }
    }
}

class LeaveRequestDialog extends JDialog {
    private JTextField startField, endField;
    private JTextArea reasonArea;

    public LeaveRequestDialog(JFrame parent) {
        super(parent, "Request Leave", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Start Date (yyyy-mm-dd):"));
        startField = new JTextField();
        formPanel.add(startField);

        formPanel.add(new JLabel("End Date (yyyy-mm-dd):"));
        endField = new JTextField();
        formPanel.add(endField);

        formPanel.add(new JLabel("Reason:"));
        reasonArea = new JTextArea();
        reasonArea.setLineWrap(true);
        formPanel.add(new JScrollPane(reasonArea));

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Submit Request");
        saveBtn.setBackground(new Color(0, 120, 215));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> submitRequest());

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(cancelBtn);
        btnPanel.add(saveBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void submitRequest() {
        try {
            LeaveRequest req = new LeaveRequest();
            req.setEmployeeId(Session.currentUser.getId());
            req.setStartDate(java.sql.Date.valueOf(startField.getText()));
            req.setEndDate(java.sql.Date.valueOf(endField.getText()));
            req.setReason(reasonArea.getText());

            if (new LeaveDAO().addLeaveRequest(req)) {
                JOptionPane.showMessageDialog(this, "Leave request submitted successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit request.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Date Format. Please use yyyy-mm-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
