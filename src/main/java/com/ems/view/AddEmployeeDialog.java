package com.ems.view;

import com.ems.dao.EmployeeDAO;
import com.ems.model.Employee;

import javax.swing.*;
import java.awt.*;

public class AddEmployeeDialog extends JDialog {
    
    private JTextField nameField, emailField, phoneField, deptField, salaryField;
    private JPasswordField passField;
    private JComboBox<String> roleBox;

    public AddEmployeeDialog(JFrame parent) {
        super(parent, "Add New Employee", true);
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        formPanel.add(passField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Department:"));
        deptField = new JTextField();
        formPanel.add(deptField);

        formPanel.add(new JLabel("Role:"));
        roleBox = new JComboBox<>(new String[]{"EMPLOYEE", "ADMIN"});
        formPanel.add(roleBox);

        formPanel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        formPanel.add(salaryField);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        saveBtn.setBackground(new Color(0, 120, 215));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> saveEmployee());

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(cancelBtn);
        btnPanel.add(saveBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void saveEmployee() {
        try {
            Employee emp = new Employee();
            emp.setFullName(nameField.getText());
            emp.setEmail(emailField.getText());
            emp.setPassword(new String(passField.getPassword()));
            emp.setPhone(phoneField.getText());
            emp.setDepartment(deptField.getText());
            emp.setRole((String) roleBox.getSelectedItem());
            emp.setSalary(Double.parseDouble(salaryField.getText()));

            EmployeeDAO dao = new EmployeeDAO();
            if (dao.addEmployee(emp)) {
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid salary format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
