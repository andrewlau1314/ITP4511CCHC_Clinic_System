/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.util;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author user
 */
public class DBConnection {
    
    public static void main(String[] args) {
        testConnection();
    }
  
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cchc_db"+ "?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    private static final String DB_USER = "root";      
    private static final String DB_PASSWORD = "";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            System.out.println("DB connect done ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


 public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("connect sussces" + new java.util.Date());
        } catch (SQLException e) {
            System.err.println("db connect fail ");
            e.printStackTrace();
        }
    }
}
