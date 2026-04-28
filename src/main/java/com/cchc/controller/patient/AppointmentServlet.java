/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cchc.controller.patient;

import com.cchc.DAO.AppointmentDB;
import com.cchc.bean.AppointmentBean;
import com.cchc.bean.UserBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author firetruck
 */
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
            int userId = currentUser.getUserId();
            int clinicId = Integer.parseInt(request.getParameter("clinicId"));
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String appointmentDateStr = request.getParameter("appointmentDate");
            String appointmentTimeStr = request.getParameter("appointmentTime");

            AppointmentBean ab = new AppointmentBean();
            ab.setUserId(userId);
            ab.setClinicId(clinicId);
            ab.setServiceId(serviceId);
            ab.setAppointmentDate(java.time.LocalDate.parse(appointmentDateStr));
            ab.setAppointmentTime(java.time.LocalTime.parse(appointmentTimeStr));

            boolean success = adb.addAppointment(ab);

            if (success) {
                request.setAttribute("message", "🎉 預約成功！");
                request.getRequestDispatcher("/views/patient/bookingSuccess.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "❌ 預約失敗！該時段可能已被預約。");
                request.getRequestDispatcher("/views/patient/timeslotList.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "系統錯誤，請稍後再試！");
            request.getRequestDispatcher("/views/patient/timeslotList.jsp").forward(request, response);
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