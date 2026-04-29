/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cchc.controller.patient;

import com.cchc.DAO.AppointmentDB;
import com.cchc.bean.AppointmentBean;
import com.cchc.bean.UserBean;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AppointmentServlet", urlPatterns = {"/book.do"})
public class AppointmentServlet extends HttpServlet {

    private AppointmentDB adb;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        adb = new AppointmentDB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean currentUser = (UserBean) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/views/common/login.jsp");
            return;
        }

        try {
            int clinicId = Integer.parseInt(request.getParameter("clinicId"));
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            LocalDate date = LocalDate.parse(request.getParameter("appointmentDate"));
            LocalTime time = LocalTime.parse(request.getParameter("appointmentTime"));

            // 新邏輯：檢查 CONFIRMED 是否已滿 10 人
            int confirmedCount = adb.countConfirmedBookings(clinicId, date, time);

            if (confirmedCount >= 10) {
                request.setAttribute("error", "❌ 此時段已滿（最多 10 人），請選擇其他時間！");
                request.getRequestDispatcher("/views/patient/timeSelection.jsp").forward(request, response);
                return;
            }

            AppointmentBean ab = new AppointmentBean();
            ab.setUserId(currentUser.getUserId());
            ab.setClinicId(clinicId);
            ab.setServiceId(serviceId);
            ab.setAppointmentDate(date);
            ab.setAppointmentTime(time);

            boolean success = adb.addAppointment(ab);

            if (success) {
                request.setAttribute("message", "🎉 預約成功！");
                request.getRequestDispatcher("/views/patient/bookingSuccess.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "❌ 預約失敗，請稍後再試！");
                request.getRequestDispatcher("/views/patient/timeSelection.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "系統錯誤，請稍後再試！");
            request.getRequestDispatcher("/views/patient/timeSelection.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}