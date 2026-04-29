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
    // 加入即日排隊（修正後版本）
       // 加入即日排隊（真實連續號碼 Q001, Q002...）
    public String joinQueue(int userId, int clinicId, int serviceId) {
        String queueNumber = "";

        // 1. 先取得今天該診所目前排隊人數
        String countSql = "SELECT COUNT(*) FROM queues WHERE clinic_id = ? AND date = CURDATE() AND status = 'WAITING'";
        int currentCount = 0;

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(countSql)) {

            ps.setInt(1, clinicId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) currentCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        queueNumber = "Q" + String.format("%03d", currentCount + 1);

        // 2. 插入排隊記錄
        String insertSql = "INSERT INTO queues (user_id, clinic_id, service_id, date, queue_number, status) " +
                           "VALUES (?, ?, ?, CURDATE(), ?, 'WAITING')";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(insertSql)) {

            ps.setInt(1, userId);
            ps.setInt(2, clinicId);
            ps.setInt(3, serviceId);
            ps.setString(4, queueNumber);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return queueNumber;   // 回傳真實號碼
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

// ==================== 取得目前正在叫的號碼 ====================
    public int getCurrentCallingNumber() {
        String sql = "SELECT MIN(CAST(SUBSTRING(queue_number, 2) AS UNSIGNED)) " +
                     "FROM queues WHERE date = CURDATE() AND status = 'WAITING'";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int num = rs.getInt(1);
                return (num > 0) ? num : 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ==================== 取得用戶自己的排隊號碼 ====================
    public String getMyQueueNumber(int userId) {
        String sql = "SELECT queue_number FROM queues " +
                     "WHERE user_id = ? AND date = CURDATE() AND status = 'WAITING' LIMIT 1";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("queue_number");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    // ==================== 取得所有診所的排隊狀態 ====================
    public ArrayList<QueueBean> getAllClinicsQueueStatus() {
        ArrayList<QueueBean> list = new ArrayList<>();
        String sql = "SELECT c.clinic_id, c.name as clinic_name, " +
                     "       COUNT(CASE WHEN q.status = 'WAITING' THEN 1 END) as waiting_count, " +
                     "       MIN(CASE WHEN q.status = 'WAITING' THEN CAST(SUBSTRING(q.queue_number, 2) AS UNSIGNED) END) as current_number " +
                     "FROM clinics c " +
                     "LEFT JOIN queues q ON c.clinic_id = q.clinic_id AND q.date = CURDATE() " +
                     "GROUP BY c.clinic_id, c.name " +
                     "ORDER BY c.clinic_id";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                QueueBean qb = new QueueBean();
                qb.setClinicId(rs.getInt("clinic_id"));
                qb.setClinicName(rs.getString("clinic_name"));
                qb.setEstimatedWaitMin(rs.getInt("waiting_count"));   // 暫借這個欄位存等待人數
                int current = rs.getInt("current_number");
                qb.setQueueNumber(current > 0 ? "Q" + String.format("%03d", current) : "目前無排隊");
                list.add(qb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // ==================== 取得用戶在特定診所的排隊號碼 ====================
    public String getMyQueueNumberInClinic(int userId, int clinicId) {
        String sql = "SELECT queue_number FROM queues " +
                     "WHERE user_id = ? AND clinic_id = ? AND date = CURDATE() AND status = 'WAITING' LIMIT 1";
        
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, clinicId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("queue_number");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}