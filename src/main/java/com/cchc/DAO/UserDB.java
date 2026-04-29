/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.DAO;

import com.cchc.bean.UserBean;
import com.cchc.util.DBConnection;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class UserDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public UserDB(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    // 登入驗證（返回 UserBean 物件，如果失敗返回 null）
    public UserBean login(String username, String password) {
        UserBean ub = null;
        String sql = "SELECT * FROM users WHERE username = ? AND password = BINARY ? AND active = 1";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ub = new UserBean();
                    ub.setUserId(rs.getInt("user_id"));
                    ub.setUsername(rs.getString("username"));
                    ub.setPassword(rs.getString("password"));
                    ub.setFullName(rs.getString("full_name"));
                    ub.setEmail(rs.getString("email"));
                    ub.setPhone(rs.getString("phone"));
                    ub.setRole(rs.getString("role"));
                    ub.setClinicId(rs.getInt("clinic_id"));
                    ub.setActive(rs.getBoolean("active"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ub;
    }

    // ==================== 取得單一用戶（管理員編輯時使用） ====================
    public UserBean getUserById(int userId) {
        UserBean ub = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ub = new UserBean();
                    ub.setUserId(rs.getInt("user_id"));
                    ub.setUsername(rs.getString("username"));
                    ub.setFullName(rs.getString("full_name"));
                    ub.setEmail(rs.getString("email"));
                    ub.setPhone(rs.getString("phone"));
                    ub.setRole(rs.getString("role"));
                    ub.setClinicId(rs.getInt("clinic_id"));
                    ub.setActive(rs.getBoolean("active"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ub;
    }

   // ==================== 管理員用：取得所有用戶 ====================
    public ArrayList<UserBean> getAllUsers() {
        ArrayList<UserBean> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY role, username";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UserBean ub = new UserBean();
                ub.setUserId(rs.getInt("user_id"));
                ub.setUsername(rs.getString("username"));
                ub.setFullName(rs.getString("full_name"));
                ub.setEmail(rs.getString("email"));
                ub.setPhone(rs.getString("phone"));
                ub.setRole(rs.getString("role"));
                ub.setActive(rs.getBoolean("active"));
                list.add(ub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // ==================== 管理員用：新增用戶 ====================
    public boolean addUser(UserBean ub) {
        String sql = "INSERT INTO users (username, password, full_name, email, phone, role, clinic_id, active) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ub.getUsername());
            ps.setString(2, ub.getPassword());
            ps.setString(3, ub.getFullName());
            ps.setString(4, ub.getEmail());
            ps.setString(5, ub.getPhone());
            ps.setString(6, ub.getRole());
            ps.setInt(7, ub.getClinicId() != null ? ub.getClinicId() : 0);  // STAFF 才需要 clinic_id
            ps.setBoolean(8, ub.isActive());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ==================== 管理員用：停用用戶（軟刪除） ====================
    public boolean deactivateUser(int userId) {
        String sql = "UPDATE users SET active = 0 WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ==================== 管理員用：更新用戶資料 ====================
    public boolean updateUser(UserBean ub) {
        String sql = "UPDATE users SET full_name = ?, email = ?, phone = ?, role = ?, " +
                     "clinic_id = ?, active = ? WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ub.getFullName());
            ps.setString(2, ub.getEmail());
            ps.setString(3, ub.getPhone());
            ps.setString(4, ub.getRole());
            ps.setInt(5, ub.getClinicId() != null ? ub.getClinicId() : 0);
            ps.setBoolean(6, ub.isActive());
            ps.setInt(7, ub.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
