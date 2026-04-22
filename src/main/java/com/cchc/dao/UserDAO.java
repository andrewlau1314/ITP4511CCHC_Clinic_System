/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.dao;
import com.cchc.model.User;
import com.cchc.util.DBConnection;
import java.sql.*;
/**
 *
 * @author user
 */
public class UserDAO {
    
    // 登入驗證（返回 User 物件，如果失敗返回 null）
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = BINARY ? AND active = 1";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setRole(rs.getString("role"));
//                    user.setClinicId(rs.getObject("clinic_id", Integer.class));
                    user.setActive(rs.getBoolean("active"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 以後會用到：取得單一用戶（可擴充）
    public User getUserById(int userId) {
        // 之後再實作，現在先不用
        return null;
    }

}
