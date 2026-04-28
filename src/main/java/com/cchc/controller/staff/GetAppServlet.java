/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cchc.controller.staff;

import com.cchc.DAO.AppointmentDB;
import com.cchc.DAO.ServiceDB;
import com.cchc.bean.AppointmentBean;
import com.cchc.bean.ClinicBean;
import com.cchc.bean.ServiceBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
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
@WebServlet(name = "GetAppServlet", urlPatterns = {"/views/staff/GetAppServlet"})
public class GetAppServlet extends HttpServlet {

    AppointmentDB adb;
    ServiceDB sdb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        adb = new AppointmentDB(dbUrl, dbUser, dbPassword);
        sdb = new ServiceDB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetAppServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetAppServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        ClinicBean cb = (ClinicBean) session.getAttribute("currentClinic");
        AppointmentBean ab = new AppointmentBean();

        try {

            int clinicId = cb.getClinicId();
            ab.setClinicId(clinicId);

            ArrayList<ServiceBean> sbs = sdb.queryServiceByClinicId(clinicId);
            req.setAttribute("serviceList", sbs);

            String fullName = req.getParameter("fullname");
            String status = req.getParameter("status");
            String dateStr = req.getParameter("date");
            String appIdStr = req.getParameter("appointmentId");
            String serviceIdStr = req.getParameter("serviceId");

            if (fullName != null && !fullName.isEmpty()) {
                ab.setFullName(fullName);
            }

            if (status != null && !status.isEmpty()) {
                ab.setStatus(status);
            }

            if (dateStr != null && !dateStr.isEmpty()) {
                ab.setAppointmentDate(LocalDate.parse(dateStr));
            }

            if (appIdStr != null && !appIdStr.isEmpty()) {
                ab.setAppointmentId(Integer.parseInt(appIdStr));
            }
            if (serviceIdStr != null && !serviceIdStr.isEmpty()) {
                ab.setServiceId(Integer.parseInt(serviceIdStr));
            }

            ArrayList<AppointmentBean> abs = adb.queryAppointments(ab);

            req.setAttribute("appointments", abs);

            req.getRequestDispatcher("dashboard.jsp?page=appointmentList").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
