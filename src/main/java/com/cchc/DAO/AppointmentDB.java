/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.DAO;

/**
 *
 * @author firetruck
 */
import com.cchc.bean.AppointmentBean;
import com.cchc.util.DBConnection;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;

public class AppointmentDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public AppointmentDB(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

//================Get Appointment====================
    public ArrayList<AppointmentBean> queryAppointments(AppointmentBean ab) {
        ArrayList<AppointmentBean> abs = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM appointments WHERE is_deleted = 0");

        if (ab.getClinicId() != 0) {
            sql.append(" AND clinic_id = ?");
        }
        if (ab.getAppointmentId() != 0) {
            sql.append(" AND appointment_id = ?");
        }
        if (ab.getUserId() != 0) {
            sql.append(" AND user_id = ?");
        }
        if (ab.getServiceId() != 0) {
            sql.append(" AND service_id = ?");
        }
        if (ab.getAppointmentDate() != null) {
            sql.append(" AND appointment_date = ?");
        }
        if (ab.getStatus() != null && !ab.getStatus().isEmpty()) {
            sql.append(" AND status = ?");
        }

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (ab.getClinicId() != 0) {
                ps.setInt(paramIndex++, ab.getClinicId());
            }
            if (ab.getAppointmentId() != 0) {
                ps.setInt(paramIndex++, ab.getAppointmentId());
            }
            if (ab.getUserId() != 0) {
                ps.setInt(paramIndex++, ab.getUserId());
            }
            if (ab.getServiceId() != 0) {
                ps.setInt(paramIndex++, ab.getServiceId());
            }
            if (ab.getAppointmentDate() != null) {
                ps.setDate(paramIndex++, java.sql.Date.valueOf(ab.getAppointmentDate()));
            }
            if (ab.getStatus() != null && !ab.getStatus().isEmpty()) {
                ps.setString(paramIndex++, ab.getStatus());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //result AppointmentBean
                    AppointmentBean rsab = new AppointmentBean();
                    rsab.setAppointmentId(rs.getInt("appointment_id"));
                    rsab.setUserId(rs.getInt("user_id"));
                    rsab.setClinicId(rs.getInt("clinic_id"));
                    rsab.setServiceId(rs.getInt("service_id"));
                    rsab.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                    rsab.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                    rsab.setStatus(rs.getString("status"));
                    rsab.setCancelReason(rs.getString("cancel_reason"));
                    rsab.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                    abs.add(rsab);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abs;
    }

//================Get Appointment====================
    public boolean addAppointment(AppointmentBean ab) {
        String sql = "INSERT INTO appointments (user_id, clinic_id, service_id, appointment_date, appointment_time) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ab.getUserId());
            ps.setInt(2, ab.getClinicId());
            ps.setInt(3, ab.getServiceId());
            ps.setDate(4, Date.valueOf(ab.getAppointmentDate()));
            ps.setTime(5, Time.valueOf(ab.getAppointmentTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

//================update Appointment====================
    public boolean updateStatus(AppointmentBean ab) {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ? AND clinic_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ab.getStatus());
            ps.setInt(2, ab.getAppointmentId());
            ps.setInt(3, ab.getClinicId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCancelReason(AppointmentBean ab) {
        String sql = "UPDATE appointments SET cancel_reason = ? WHERE appointment_id = ? AND clinic_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ab.getCancelReason());
            ps.setInt(2, ab.getAppointmentId());
            ps.setInt(3, ab.getClinicId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAppointmentDate(AppointmentBean ab) {
        String sql = "UPDATE appointments SET appointment_date = ? WHERE appointment_id = ? AND clinic_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(ab.getAppointmentDate()));
            ps.setInt(2, ab.getAppointmentId());
            ps.setInt(3, ab.getClinicId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAppointmentTime(AppointmentBean ab) {
        String sql = "UPDATE appointments SET appointment_time = ? WHERE appointment_id = ? AND clinic_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTime(1, java.sql.Time.valueOf(ab.getAppointmentTime()));
            ps.setInt(2, ab.getAppointmentId());
            ps.setInt(3, ab.getClinicId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateServiceId(AppointmentBean ab) {
        String sql = "UPDATE appointments SET service_id = ? WHERE appointment_id = ? AND clinic_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ab.getServiceId());
            ps.setInt(2, ab.getAppointmentId());
            ps.setInt(3, ab.getClinicId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//================update Appointment====================

//================delete====================
    public boolean deleteAppointment(AppointmentBean ab) {
        String sql = "UPDATE appointments SET is_deleted = 1 WHERE clinic_id = ? AND appointment_id = ?";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ab.getClinicId());
            ps.setInt(2, ab.getAppointmentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//================delete====================
}
