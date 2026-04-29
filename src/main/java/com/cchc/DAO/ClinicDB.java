/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.DAO;

/**
 *
 * @author firetruck
 */
import com.cchc.bean.ClinicBean;
import com.cchc.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class ClinicDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public ClinicDB(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public ArrayList<ClinicBean> getClinics() {
        ArrayList<ClinicBean> cbs = new ArrayList<>();
        String sql = "SELECT * FROM clinics";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ClinicBean cb = new ClinicBean();
                cb.setClinicId(rs.getInt("clinic_id"));
                cb.setName(rs.getString("name"));
                cb.setPhone(rs.getString("phone"));
                cb.setAddress(rs.getString("address"));
                cbs.add(cb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cbs;
    }

    public ClinicBean queryClinicById(int id) {
        ClinicBean cb = null;
        String sql = "SELECT * FROM clinics WHERE clinic_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cb = new ClinicBean();
                    cb.setClinicId(rs.getInt("clinic_id"));
                    cb.setName(rs.getString("name"));
                    cb.setPhone(rs.getString("phone"));
                    cb.setAddress(rs.getString("address"));
                    cb.setDayOff(rs.getString("day_off"));
                    cb.setOpenTime(rs.getTime("open_time").toLocalTime());
                    cb.setCloseTime(rs.getTime("close_time").toLocalTime());
                    cb.setLunchBreakStart(rs.getTime("lunch_break_start").toLocalTime());
                    cb.setLunchBreakEnd(rs.getTime("lunch_break_end").toLocalTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cb;
    }

}
