package com.ems.model;

import java.sql.Date;

public class Employee {
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String department;
    private String role; // 'ADMIN' or 'EMPLOYEE'
    private double salary;
    private Date joinDate;

    public Employee() {}

    public Employee(int id, String fullName, String email, String password, String phone, String department, String role, double salary, Date joinDate) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.department = department;
        this.role = role;
        this.salary = salary;
        this.joinDate = joinDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }
}
