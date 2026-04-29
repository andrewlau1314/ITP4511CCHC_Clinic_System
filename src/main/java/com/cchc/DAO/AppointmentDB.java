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

        StringBuilder sql = new StringBuilder(
                "SELECT a.*, u.full_name, s.service_name FROM appointments a "
                + "JOIN users u ON a.user_id = u.user_id "
                + "JOIN services s ON a.service_id = s.service_id "
                + "WHERE a.is_deleted = 0"
        );

        if (ab.getClinicId() != 0) {
            sql.append(" AND a.clinic_id = ?");
        }
        if (ab.getAppointmentId() != 0) {
            sql.append(" AND a.appointment_id = ?");
        }
        if (ab.getUserId() != 0) {
            sql.append(" AND a.user_id = ?");
        }
        if (ab.getFullName() != null && !ab.getFullName().isEmpty()) {
            sql.append(" AND u.full_name = ?");
        }
        if (ab.getServiceId() != 0) {
            sql.append(" AND a.service_id = ?");
        }
        if (ab.getAppointmentDate() != null) {
            sql.append(" AND a.appointment_date = ?");
        }
        if (ab.getStatus() != null && !ab.getStatus().isEmpty()) {
            sql.append(" AND a.status = ?");
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
            if (ab.getFullName() != null && !ab.getFullName().isEmpty()) {
                ps.setString(paramIndex++, ab.getFullName());
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
                    //AppointmentBean result 
                    AppointmentBean abrs = new AppointmentBean();

                    abrs.setAppointmentId(rs.getInt("appointment_id"));
                    abrs.setUserId(rs.getInt("user_id"));
                    abrs.setClinicId(rs.getInt("clinic_id"));
                    abrs.setServiceId(rs.getInt("service_id"));
                    abrs.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                    abrs.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                    abrs.setStatus(rs.getString("status"));
                    abrs.setCancelReason(rs.getString("cancel_reason"));
                    abrs.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                    abrs.setFullName(rs.getString("full_name"));
                    abrs.setServiceName(rs.getString("service_name"));

                    abs.add(abrs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abs;
    }

    public AppointmentBean getAppointmentById(int appId) {
        String sql = "SELECT a.*, u.full_name, s.service_name FROM appointments a "
                + "JOIN users u ON a.user_id = u.user_id "
                + "JOIN services s ON a.service_id = s.service_id "
                + "WHERE a.appointment_id = ? AND a.is_deleted = 0";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AppointmentBean ab = new AppointmentBean();
                    ab.setAppointmentId(rs.getInt("appointment_id"));
                    ab.setUserId(rs.getInt("user_id"));
                    ab.setClinicId(rs.getInt("clinic_id"));
                    ab.setServiceId(rs.getInt("service_id"));
                    ab.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                    ab.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                    ab.setStatus(rs.getString("status"));
                    ab.setCancelReason(rs.getString("cancel_reason"));
                    ab.setFullName(rs.getString("full_name"));
                    ab.setServiceName(rs.getString("service_name"));
                    return ab;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 這會在 Tomcat Console 顯示報錯內容
        }
        return null;
    }

    public int getConfirmedCount(int clinicId, int serviceId, LocalDate date) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE clinic_id = ? AND service_id = ? "
                + "AND appointment_date = ? AND is_deleted = 0 AND status IN ('CONFIRMED', 'COMPLETED')";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clinicId);
            ps.setInt(2, serviceId);
            ps.setDate(3, java.sql.Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isTimeConflicting(int userId, LocalDate date, LocalTime time, int currentAppId) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE user_id = ? AND appointment_date = ? "
                + "AND appointment_time = ? AND is_deleted = 0 AND appointment_id != ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setTime(3, java.sql.Time.valueOf(time));
            ps.setInt(4, currentAppId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isTimeSlotTaken(int clinicId, LocalDate date, LocalTime time, int currentAppId) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE clinic_id = ? AND appointment_date = ? "
                + "AND appointment_time = ? AND is_deleted = 0 AND appointment_id != ? "
                + "AND status IN ('CONFIRMED', 'COMPLETED')";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clinicId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setTime(3, java.sql.Time.valueOf(time));
            ps.setInt(4, currentAppId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
    public boolean updateAppointment(AppointmentBean ab) {
        String sql = "UPDATE appointments SET service_id = ?, appointment_date = ?, "
                + "appointment_time = ?, status = ?, cancel_reason = ? "
                + "WHERE appointment_id = ? AND clinic_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ab.getServiceId());
            ps.setDate(2, java.sql.Date.valueOf(ab.getAppointmentDate()));
            ps.setTime(3, java.sql.Time.valueOf(ab.getAppointmentTime()));
            ps.setString(4, ab.getStatus());
            ps.setString(5, ab.getCancelReason());
            ps.setInt(6, ab.getAppointmentId());
            ps.setInt(7, ab.getClinicId());

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
