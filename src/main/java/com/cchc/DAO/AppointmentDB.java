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
import java.util.Map;

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


// ==================== 取得某診所 + 某服務的可用時段 Get Clinic and check this Clininc what time can be booking====================
    public ArrayList<AppointmentBean> getAvailableTimeslots(int clinicId, int serviceId) {
        ArrayList<AppointmentBean> list = new ArrayList<>();
        String sql = "SELECT * FROM timeslots WHERE clinic_id = ? AND service_id = ? AND booked < quota ORDER BY date, start_time";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clinicId);
            ps.setInt(2, serviceId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AppointmentBean ab = new AppointmentBean();
                    ab.setAppointmentId(rs.getInt("timeslot_id"));   // 借用 appointment_id 欄位存 timeslot_id
                    ab.setClinicId(rs.getInt("clinic_id"));
                    ab.setServiceId(rs.getInt("service_id"));
                    ab.setAppointmentDate(rs.getDate("date").toLocalDate());
                    ab.setAppointmentTime(rs.getTime("start_time").toLocalTime());
                    // 可自行加入 end_time 如果 Bean 有這個欄位
                    list.add(ab);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // ==================== 新邏輯：檢查該時段已確認預約數量 ====================
    public int countConfirmedBookings(int clinicId, java.time.LocalDate date, java.time.LocalTime time) {
        String sql = "SELECT COUNT(*) FROM appointments " +
                     "WHERE clinic_id = ? AND appointment_date = ? " +
                     "AND appointment_time = ? AND status = 'CONFIRMED' AND is_deleted = 0";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clinicId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setTime(3, java.sql.Time.valueOf(time));

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

    // ==================== 新邏輯：取得某日期的所有可能時間段（可自訂） ====================
    public ArrayList<String> getAvailableTimesForDate(int clinicId, int serviceId, java.time.LocalDate date) {
        ArrayList<String> times = new ArrayList<>();
        // 這裡先用固定時段（可之後改成從資料庫讀取）
        String[] possibleTimes = {"09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", 
                                  "14:00:00", "14:30:00", "15:00:00", "15:30:00"};

        for (String t : possibleTimes) {
            java.time.LocalTime time = java.time.LocalTime.parse(t);
            int count = countConfirmedBookings(clinicId, date, time);
            if (count < 10) {
                times.add(t);
            }
        }
        return times;
    }

    // ==================== 查詢某用戶的所有預約 Check User Appointment====================
    public ArrayList<AppointmentBean> queryAppointmentsByUser(int userId) {
        ArrayList<AppointmentBean> list = new ArrayList<>();
        String sql = "SELECT a.*, c.name as clinic_name, s.service_name " +
                     "FROM appointments a " +
                     "JOIN clinics c ON a.clinic_id = c.clinic_id " +
                     "JOIN services s ON a.service_id = s.service_id " +
                     "WHERE a.user_id = ? AND a.is_deleted = 0 " +
                     "ORDER BY a.appointment_date DESC, a.appointment_time DESC";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AppointmentBean ab = new AppointmentBean();
                    ab.setAppointmentId(rs.getInt("appointment_id"));
                    ab.setClinicId(rs.getInt("clinic_id"));
                    ab.setServiceId(rs.getInt("service_id"));
                    ab.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                    ab.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                    ab.setStatus(rs.getString("status"));
                    ab.setCancelReason(rs.getString("cancel_reason"));
                    // 可自行加入 clinicName 和 serviceName 如果你的 Bean 有這些欄位
                    list.add(ab);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==================== 取消預約 Cancel Appointment by UserSide====================
    public boolean cancelAppointment(int appointmentId, int userId) {
        String sql = "UPDATE appointments SET status = 'CANCELLED', cancel_reason = '用戶自行取消' " +
                     "WHERE appointment_id = ? AND user_id = ? AND status = 'PENDING' AND is_deleted = 0";
        
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appointmentId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
        // ==================== 報表用：總預約數 ====================
    public int getTotalBookings() {
        String sql = "SELECT COUNT(*) FROM appointments WHERE is_deleted = 0";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ==================== 報表用：依狀態統計 ====================
    public int getBookingsByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE status = ? AND is_deleted = 0";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
        // ==================== 每月預約統計 ====================
    public Map<String, Integer> getMonthlyBookings() {
        // 簡化版：最近6個月統計
        // 你可以之後再擴充成更完整的
        return new java.util.HashMap<>(); // 先留空，之後再實作詳細版
    }

    // ==================== 各診所預約統計 ====================
    public ArrayList<Map<String, Object>> getBookingsByClinic() {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT c.name as clinic_name, COUNT(a.appointment_id) as total, " +
                     "SUM(CASE WHEN a.status = 'CONFIRMED' THEN 1 ELSE 0 END) as confirmed " +
                     "FROM clinics c LEFT JOIN appointments a ON c.clinic_id = a.clinic_id " +
                     "WHERE a.is_deleted = 0 GROUP BY c.clinic_id, c.name ORDER BY total DESC";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> map = new java.util.HashMap<>();
                map.put("clinicName", rs.getString("clinic_name"));
                map.put("total", rs.getInt("total"));
                map.put("confirmed", rs.getInt("confirmed"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    

    
        // ==================== 報表用：顯示名稱 ====================
    private String clinicName;
    private String patientName;

    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
            // ==================== 管理員報表：依條件查詢預約（顯示名稱） ====================
    public ArrayList<AppointmentBean> getAppointmentsWithFilter(Integer clinicId, Integer serviceId, 
                                                                String monthYear, String status) {
        ArrayList<AppointmentBean> list = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT a.*, c.name as clinic_name, u.full_name as patient_name " +
            "FROM appointments a " +
            "LEFT JOIN clinics c ON a.clinic_id = c.clinic_id " +
            "LEFT JOIN users u ON a.user_id = u.user_id " +
            "WHERE a.is_deleted = 0"
        );

        if (clinicId != null) sql.append(" AND a.clinic_id = ?");
        if (serviceId != null) sql.append(" AND a.service_id = ?");
        if (monthYear != null && !monthYear.isEmpty()) sql.append(" AND DATE_FORMAT(a.appointment_date, '%Y-%m') = ?");
        if (status != null && !status.isEmpty()) sql.append(" AND a.status = ?");

        sql.append(" ORDER BY a.appointment_date DESC, a.appointment_time DESC");

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (clinicId != null) ps.setInt(paramIndex++, clinicId);
            if (serviceId != null) ps.setInt(paramIndex++, serviceId);
            if (monthYear != null && !monthYear.isEmpty()) ps.setString(paramIndex++, monthYear);
            if (status != null && !status.isEmpty()) ps.setString(paramIndex++, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AppointmentBean ab = new AppointmentBean();
                    ab.setAppointmentId(rs.getInt("appointment_id"));
                    ab.setUserId(rs.getInt("user_id"));
                    ab.setClinicId(rs.getInt("clinic_id"));
                    ab.setServiceId(rs.getInt("service_id"));
                    ab.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                    ab.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
                    ab.setStatus(rs.getString("status"));
                    ab.setCancelReason(rs.getString("cancel_reason"));

                    // 新增名稱
                    ab.setClinicName(rs.getString("clinic_name"));
                    ab.setPatientName(rs.getString("patient_name"));

                    list.add(ab);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
