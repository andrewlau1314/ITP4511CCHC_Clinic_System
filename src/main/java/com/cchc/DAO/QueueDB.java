/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.DAO;

import com.cchc.bean.QueueBean;
import com.cchc.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class QueueDB {
    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public QueueDB(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    // 加入即日排隊
    public boolean joinQueue(int userId, int clinicId, int serviceId) {
        String sql = "INSERT INTO queues (user_id, clinic_id, service_id, date, queue_number, status) " +
                     "VALUES (?, ?, ?, CURDATE(), CONCAT('Q', LPAD((SELECT COUNT(*) + 1 FROM queues WHERE clinic_id = ? AND date = CURDATE()), 3, '0')), 'WAITING')";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, clinicId);
            ps.setInt(3, serviceId);
            ps.setInt(4, clinicId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 取得目前排隊狀況（今天該診所的等待人數）
    public int getCurrentQueueCount(int clinicId) {
        String sql = "SELECT COUNT(*) FROM queues WHERE clinic_id = ? AND date = CURDATE() AND status = 'WAITING'";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clinicId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}