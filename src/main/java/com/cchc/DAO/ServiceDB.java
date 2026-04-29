/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.DAO;

/**
 *
 * @author firetruck
 */
import com.cchc.bean.ServiceBean;
import com.cchc.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;


public class ServiceDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public ServiceDB(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public ArrayList<ServiceBean> queryServiceByClinicId(int clinicId) {
        ArrayList<ServiceBean> sbs = new ArrayList<>();
        String sql = "SELECT s.*, cs.quota FROM services s "
                + "JOIN clinics_services cs ON s.service_id = cs.service_id "
                + "WHERE cs.clinic_id = ?";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clinicId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ServiceBean sb = new ServiceBean();
                    sb.setServiceId(rs.getInt("service_id"));
                    sb.setServiceName(rs.getString("service_name"));
                    sb.setDescription(rs.getString("description"));
                    sb.setQuota(rs.getInt("quota"));
                    sbs.add(sb);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sbs;
        
    }
        // ==================== 管理員用：取得所有服務 ====================
    public ArrayList<ServiceBean> getAllServices() {
        ArrayList<ServiceBean> list = new ArrayList<>();
        String sql = "SELECT * FROM services ORDER BY service_name";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ServiceBean sb = new ServiceBean();
                sb.setServiceId(rs.getInt("service_id"));
                sb.setServiceName(rs.getString("service_name"));
                sb.setDescription(rs.getString("description"));
                list.add(sb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public int getServiceQuota(int clinicId, int serviceId) {
        String sql = "SELECT quota FROM clinics_services WHERE clinic_id = ? AND service_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clinicId);
            ps.setInt(2, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quota");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }        
        return 0;
    }
    
        // ==================== 管理員用：新增服務 ====================
    public boolean addService(ServiceBean sb) {
        String sql = "INSERT INTO services (service_name, description) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sb.getServiceName());
            ps.setString(2, sb.getDescription());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
        // ==================== 取得單一服務（編輯時使用） ====================
    public ServiceBean queryServiceById(int serviceId) {
        ServiceBean sb = null;
        String sql = "SELECT * FROM services WHERE service_id = ?";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sb = new ServiceBean();
                    sb.setServiceId(rs.getInt("service_id"));
                    sb.setServiceName(rs.getString("service_name"));
                    sb.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb;
    }
    
        // ==================== 更新服務 ====================
    public boolean updateService(ServiceBean sb) {
        String sql = "UPDATE services SET service_name = ?, description = ? WHERE service_id = ?";

        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sb.getServiceName());
            ps.setString(2, sb.getDescription());
            ps.setInt(3, sb.getServiceId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
        // ==================== 刪除診所的所有服務關聯（更新前先清除舊資料） ====================
    public boolean deleteClinicServices(int clinicId) {
        String sql = "DELETE FROM clinics_services WHERE clinic_id = ?";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clinicId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==================== 新增診所與服務的關聯（含配額） ====================
    public boolean addClinicService(int clinicId, int serviceId, int quota) {
        String sql = "INSERT INTO clinics_services (clinic_id, service_id, quota) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clinicId);
            ps.setInt(2, serviceId);
            ps.setInt(3, quota);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
