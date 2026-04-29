/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cchc.controller.staff;

import com.cchc.DAO.AppointmentDB;
import com.cchc.DAO.ServiceDB;
import com.cchc.bean.AppointmentBean;
import com.cchc.bean.ServiceBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author firetruck
 */
@WebServlet(name = "EditAppServlet", urlPatterns = {"/views/staff/EditAppServlet"})
public class EditAppServlet extends HttpServlet {

    private AppointmentDB adb;
    private ServiceDB sdb;

    @Override
    public void init() {
        String dbUser = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        adb = new AppointmentDB(dbUrl, dbUser, dbPassword);
        sdb = new ServiceDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {

            int appId = Integer.parseInt(req.getParameter("id"));

            AppointmentBean app = adb.getAppointmentById(appId);

            if (app != null) {
                int maxQuota = sdb.getServiceQuota(app.getClinicId(), app.getServiceId());
                int confirmedCount = adb.getConfirmedCount(app.getClinicId(), app.getServiceId(), app.getAppointmentDate());
                int remaining = maxQuota - confirmedCount;

                ArrayList<ServiceBean> serviceList = sdb.queryServiceByClinicId(app.getClinicId());

                req.setAttribute("app", app);
                req.setAttribute("serviceList", serviceList);
                req.setAttribute("remainingQuota", remaining);

                req.getRequestDispatcher("edit_appointment.jsp").forward(req, res);

            } else {
                res.sendRedirect("GetAppServlet?error=notfound");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("GetAppServlet");
        }
    }
}
