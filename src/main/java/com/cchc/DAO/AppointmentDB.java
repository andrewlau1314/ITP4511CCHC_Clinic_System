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
    public ArrayList<AppointmentBean> queryAppointments() {
        ArrayList<AppointmentBean> abs = new ArrayList<>();
        AppointmentBean ab = null;

        String sql = "SELECT * FROM appointments AND is_deleted = 0";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ab = new AppointmentBean();
                ab.setAppointmentId(rs.getInt("appointment_id"));
                ab.setUserId(rs.getInt("user_id"));
                ab.setClinicId(rs.getInt("clinic_id"));
                ab.setServiceId(rs.getInt("service_id"));
                ab.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                ab.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                ab.setStatus(rs.getString("status"));
                ab.setCancelReason(rs.getString("cancel_reason"));
                ab.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                abs.add(ab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abs;
    }

    public ArrayList<AppointmentBean> queryAppointmentByClinicId(int clinicId) {
        ArrayList<AppointmentBean> abs = new ArrayList<>();
        AppointmentBean ab = null;
        String sql = "SELECT * FROM appointments WHERE clinic_id = ? AND is_deleted = 0";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clinicId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ab = new AppointmentBean();
                    ab.setAppointmentId(rs.getInt("appointment_id"));
                    ab.setUserId(rs.getInt("user_id"));
                    ab.setClinicId(rs.getInt("clinic_id"));
                    ab.setServiceId(rs.getInt("service_id"));
                    ab.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                    ab.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                    ab.setStatus(rs.getString("status"));
                    ab.setCancelReason(rs.getString("cancel_reason"));
                    ab.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                    abs.add(ab);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return abs;
    }

    public AppointmentBean queryAppointmentById(int appointmentId, int clinicId) {
        AppointmentBean ab = null;
        String sql = "SELECT * FROM appointments WHERE appointment_id = ? AND clinic_id = ? AND is_deleted = 0";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appointmentId);
            ps.setInt(2, clinicId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ab = new AppointmentBean();
                    ab.setAppointmentId(rs.getInt("appointment_id"));
                    ab.setUserId(rs.getInt("user_id"));
                    ab.setClinicId(rs.getInt("clinic_id"));
                    ab.setServiceId(rs.getInt("service_id"));
                    ab.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                    ab.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                    ab.setStatus(rs.getString("status"));
                    ab.setCancelReason(rs.getString("cancel_reason"));
                    ab.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ab;
    }

    public ArrayList<AppointmentBean> queryAppointmentByUserId(int userId, int clinicId) {
        ArrayList<AppointmentBean> abs = new ArrayList<>();
        AppointmentBean ab = null;
        String sql = "SELECT * FROM appointments WHERE clinic_id = ? AND user_id = ? AND is_deleted = 0";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clinicId);
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ab = new AppointmentBean();
                    ab.setAppointmentId(rs.getInt("appointment_id"));
                    ab.setUserId(rs.getInt("user_id"));
                    ab.setClinicId(rs.getInt("clinic_id"));
                    ab.setServiceId(rs.getInt("service_id"));
                    ab.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                    ab.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                    ab.setStatus(rs.getString("status"));
                    ab.setCancelReason(rs.getString("cancel_reason"));
                    ab.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                    abs.add(ab);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return abs;
    }

    public ArrayList<AppointmentBean> queryAppointmentByDate(LocalDate appointmentDate, int clinicId) {
        ArrayList<AppointmentBean> abs = new ArrayList<>();
        AppointmentBean ab = null;
        String sql = "SELECT * FROM appointments WHERE clinic_id = ? AND appointment_date = ? AND is_deleted = 0";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clinicId);
            ps.setDate(2, java.sql.Date.valueOf(appointmentDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ab = new AppointmentBean();
                    ab.setAppointmentId(rs.getInt("appointment_id"));
                    ab.setUserId(rs.getInt("user_id"));
                    ab.setClinicId(rs.getInt("clinic_id"));
                    ab.setServiceId(rs.getInt("service_id"));
                    ab.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                    ab.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                    ab.setStatus(rs.getString("status"));
                    ab.setCancelReason(rs.getString("cancel_reason"));
                    ab.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                    abs.add(ab);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return abs;
    }

    public ArrayList<AppointmentBean> queryAppointmentByStatus(String status, int clinicId) {
        ArrayList<AppointmentBean> abs = new ArrayList<>();
        AppointmentBean ab = null;
        String sql = "SELECT * FROM appointments WHERE clinic_id = ? AND status = ? AND is_deleted = 0";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clinicId);
            ps.setString(2, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ab = new AppointmentBean();
                    ab.setAppointmentId(rs.getInt("appointment_id"));
                    ab.setUserId(rs.getInt("user_id"));
                    ab.setClinicId(rs.getInt("clinic_id"));
                    ab.setServiceId(rs.getInt("service_id"));
                    ab.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                    ab.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                    ab.setStatus(rs.getString("status"));
                    ab.setCancelReason(rs.getString("cancel_reason"));
                    ab.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                    abs.add(ab);
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
