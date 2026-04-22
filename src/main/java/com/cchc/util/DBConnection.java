package com.cchc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static void main(String[] args) {
        testConnection();
    }

    // ==================== 你的資料庫設定 ====================
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cchc_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";   // ←←← 務必改成你自己 MySQL root 的密碼！


    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // 測試用（可保留或刪除）
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("🎉 資料庫連線成功！目前時間：" + new java.util.Date());
        } catch (SQLException e) {
            System.err.println("❌ 資料庫連線失敗！");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}