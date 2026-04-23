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
    
    
    
    
}
