package com.cchc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection(String dbUrl, String dbUser, String dbPassword) throws SQLException, IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);

    }
}
