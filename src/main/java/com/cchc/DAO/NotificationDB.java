package com.cchc.DAO;

import com.cchc.bean.NotificationBean;
import com.cchc.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class NotificationDB {
    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public NotificationDB(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    // 取得用戶的所有通知（最新在前）
    public ArrayList<NotificationBean> getNotificationsByUser(int userId) {
        ArrayList<NotificationBean> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NotificationBean nb = new NotificationBean();
                    nb.setNotifId(rs.getInt("notif_id"));
                    nb.setUserId(rs.getInt("user_id"));
                    nb.setTitle(rs.getString("title"));
                    nb.setMessage(rs.getString("message"));
                    nb.setRead(rs.getBoolean("is_read"));
                    nb.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    nb.setType(rs.getString("type"));
                    list.add(nb);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 標記通知為已讀
    public boolean markAsRead(int notifId, int userId) {
        String sql = "UPDATE notifications SET is_read = 1 WHERE notif_id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, notifId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}