/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
/**
 *
 * @author user
 */
@WebServlet(name = "RescheduleServlet", urlPatterns = {"/patient/reschedule.do"})
public class RescheduleServlet extends HttpServlet {

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

        if ("GET".equals(request.getMethod())) {
            // 顯示改期表單
            try {
                int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
                AppointmentBean ab = adb.getAppointmentById(appointmentId);   // 需要這個方法

                if (ab != null && ab.getUserId() == currentUser.getUserId()) {
                    request.setAttribute("appointment", ab);
                    request.getRequestDispatcher("/views/patient/reschedule.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/patient/myBookings.do");
                }
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/patient/myBookings.do");
            }
        } else {
            // POST：執行改期
            try {
                int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
                LocalDate newDate = LocalDate.parse(request.getParameter("appointmentDate"));
                LocalTime newTime = LocalTime.parse(request.getParameter("appointmentTime"));

                AppointmentBean ab = new AppointmentBean();
                ab.setAppointmentId(appointmentId);
                ab.setAppointmentDate(newDate);
                ab.setAppointmentTime(newTime);

                boolean success = adb.updateAppointmentDate(ab) && adb.updateAppointmentTime(ab);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/patient/myBookings.do");
                } else {
                    request.setAttribute("error", "❌ 改期失敗！");
                    request.getRequestDispatcher("/views/patient/reschedule.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "❌ 系統錯誤！");
                request.getRequestDispatcher("/views/patient/reschedule.jsp").forward(request, response);
            }
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
