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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cb;
    }
    
        // ==================== 管理員用：新增診所 ====================
    public boolean addClinic(ClinicBean cb) {
        String sql = "INSERT INTO clinics (name, address, phone, day_off, lunch_break_start, lunch_break_end, open_time, close_time) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cb.getName());
            ps.setString(2, cb.getAddress());
            ps.setString(3, cb.getPhone());
            ps.setString(4, cb.getDayOff() != null ? cb.getDayOff() : "");

            // 修正：LocalTime → java.sql.Time
            ps.setTime(5, cb.getLunchBreakStart() != null ? Time.valueOf(cb.getLunchBreakStart()) : null);
            ps.setTime(6, cb.getLunchBreakEnd() != null ? Time.valueOf(cb.getLunchBreakEnd()) : null);
            ps.setTime(7, cb.getOpenTime() != null ? Time.valueOf(cb.getOpenTime()) : null);
            ps.setTime(8, cb.getCloseTime() != null ? Time.valueOf(cb.getCloseTime()) : null);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
        // ==================== 管理員用：更新診所 ====================
    public boolean updateClinic(ClinicBean cb) {
        String sql = "UPDATE clinics SET name=?, address=?, phone=?, day_off=?, " +
                     "lunch_break_start=?, lunch_break_end=?, open_time=?, close_time=? " +
                     "WHERE clinic_id=?";
        
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cb.getName());
            ps.setString(2, cb.getAddress());
            ps.setString(3, cb.getPhone());
            ps.setString(4, cb.getDayOff() != null ? cb.getDayOff() : "");

            ps.setTime(5, cb.getLunchBreakStart() != null ? Time.valueOf(cb.getLunchBreakStart()) : null);
            ps.setTime(6, cb.getLunchBreakEnd() != null ? Time.valueOf(cb.getLunchBreakEnd()) : null);
            ps.setTime(7, cb.getOpenTime() != null ? Time.valueOf(cb.getOpenTime()) : null);
            ps.setTime(8, cb.getCloseTime() != null ? Time.valueOf(cb.getCloseTime()) : null);

            ps.setInt(9, cb.getClinicId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
