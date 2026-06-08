package com.ems.dao;

import com.ems.config.DatabaseConnection;
import com.ems.model.Attendance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public List<Attendance> getAllAttendances() {
        List<Attendance> list = new ArrayList<>();
        // Join with employees table to get the name
        String query = "SELECT a.*, e.full_name FROM attendance a " +
                       "JOIN employees e ON a.employee_id = e.id " +
                       "ORDER BY a.check_in DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Attendance att = new Attendance();
                att.setId(rs.getInt("id"));
                att.setEmployeeId(rs.getInt("employee_id"));
                att.setEmployeeName(rs.getString("full_name"));
                att.setCheckIn(rs.getTimestamp("check_in"));
                att.setCheckOut(rs.getTimestamp("check_out"));
                att.setStatus(rs.getString("status"));
                list.add(att);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Attendance> getAttendanceByEmployee(int employeeId) {
        List<Attendance> list = new ArrayList<>();
        String query = "SELECT a.*, e.full_name FROM attendance a " +
                       "JOIN employees e ON a.employee_id = e.id " +
                       "WHERE a.employee_id = ? ORDER BY a.check_in DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Attendance att = new Attendance();
                att.setId(rs.getInt("id"));
                att.setEmployeeId(rs.getInt("employee_id"));
                att.setEmployeeName(rs.getString("full_name"));
                att.setCheckIn(rs.getTimestamp("check_in"));
                att.setCheckOut(rs.getTimestamp("check_out"));
                att.setStatus(rs.getString("status"));
                list.add(att);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean clockIn(int employeeId) {
        String query = "INSERT INTO attendance (employee_id, check_in, status) VALUES (?, CURRENT_TIMESTAMP, 'PRESENT')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setInt(1, employeeId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clockOut(int employeeId) {
        // Update the most recent open attendance record for today
        String query = "UPDATE attendance SET check_out = CURRENT_TIMESTAMP " +
                       "WHERE employee_id = ? AND check_out IS NULL " +
                       "ORDER BY check_in DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setInt(1, employeeId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
