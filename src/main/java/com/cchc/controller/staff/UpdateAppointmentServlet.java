/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cchc.controller.staff;

import com.cchc.DAO.AppointmentDB;
import com.cchc.DAO.ServiceDB;
import com.cchc.bean.AppointmentBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author firetruck
 */
@WebServlet(name = "UpdateAppointmentServlet", urlPatterns = {"/views/staff/UpdateAppointmentServlet"})
public class UpdateAppointmentServlet extends HttpServlet {

    AppointmentDB adb;
    ServiceDB sdb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        adb = new AppointmentDB(dbUrl, dbUser, dbPassword);
        sdb = new ServiceDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            int appId = Integer.parseInt(req.getParameter("appointmentId"));
            String status = req.getParameter("status");
            String reason = req.getParameter("cancelReason");


            AppointmentBean current = adb.getAppointmentById(appId);
            if (current == null) {
                res.sendRedirect("GetAppServlet?error=notfound");
                return;
            }

            int clinicId = current.getClinicId();
            int serviceId = current.getServiceId();
            LocalDate date = current.getAppointmentDate();
            LocalTime time = current.getAppointmentTime();

            if ("CONFIRMED".equals(status) || "COMPLETED".equals(status)) {

                boolean isBecomingConfirmed = !"CONFIRMED".equals(current.getStatus()) && !"COMPLETED".equals(current.getStatus());

                if (isBecomingConfirmed) {
                    int maxQuota = sdb.getServiceQuota(clinicId, serviceId);
                    int currentUsed = adb.getConfirmedCount(clinicId, serviceId, date);

                    if (currentUsed >= maxQuota) {
                        res.sendRedirect("EditAppServlet?id=" + appId + "&error=full");
                        return;
                    }

                    // 檢查該時段是否已被其他人佔用 (同診所、同時間、非本人)
                    if (adb.isTimeSlotTaken(clinicId, date, time, appId)) {
                        res.sendRedirect("EditAppServlet?id=" + appId + "&error=time_taken");
                        return;
                    }
                }
            }

            current.setStatus(status);
            current.setCancelReason(reason);

            if (adb.updateAppointment(current)) {
                res.sendRedirect("GetAppServlet?msg=success");
            } else {
                res.sendRedirect("EditAppServlet?id=" + appId + "&error=db");
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("GetAppServlet?error=system");
        }
    }
}