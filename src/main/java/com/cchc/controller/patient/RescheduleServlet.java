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

        try {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            LocalDate newDate = LocalDate.parse(request.getParameter("appointmentDate"));
            LocalTime newTime = LocalTime.parse(request.getParameter("appointmentTime"));

            AppointmentBean ab = new AppointmentBean();
            ab.setAppointmentId(appointmentId);
            ab.setAppointmentDate(newDate);
            ab.setAppointmentTime(newTime);

            // 使用 teammate 已經寫好的 update 方法
            boolean success = adb.updateAppointmentDate(ab) && adb.updateAppointmentTime(ab);

            if (success) {
                request.setAttribute("message", "✅ 預約已成功改期！ Appointment Date Changed");
            } else {
                request.setAttribute("error", "❌ 改期失敗，請稍後再試！ Fail to Change Appointment Date");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "系統錯誤，請稍後再試！ System Error");
        }

        // 改期完成後跳回我的預約列表
        response.sendRedirect(request.getContextPath() + "/patient/myBookings.do");
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
