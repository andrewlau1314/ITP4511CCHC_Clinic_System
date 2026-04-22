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
//                    ub.setClinicId(rs.getObject("clinic_id", Integer.class));
                    ub.setActive(rs.getBoolean("active"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ub;
    }

    // 以後會用到：取得單一用戶（可擴充）
    public UserBean getUserById(int userId) {
        // 之後再實作，現在先不用
        return null;
    }

}
