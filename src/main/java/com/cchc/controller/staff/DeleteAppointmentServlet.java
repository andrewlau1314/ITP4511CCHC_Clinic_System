/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cchc.controller.staff;

import com.cchc.DAO.AppointmentDB;
import com.cchc.bean.AppointmentBean;
import com.cchc.bean.ClinicBean;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "DeleteAppointmentServlet", urlPatterns = {"/views/staff/DeleteAppointmentServlet"})
public class DeleteAppointmentServlet extends HttpServlet {

    AppointmentDB adb;

    public void init() {
        // Initialize DB connection details as you did in GetAppServlet
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        adb = new AppointmentDB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeleteAppointmentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteAppointmentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ClinicBean cb = (ClinicBean) session.getAttribute("currentClinic");
        String appIdStr = request.getParameter("id");

        if (cb != null && appIdStr != null) {
            AppointmentBean ab = new AppointmentBean();
            ab.setAppointmentId(Integer.parseInt(appIdStr));
            ab.setClinicId(cb.getClinicId()); // Get clinic ID from session for security

            // Call the deleteAppointment method from AppointmentDB
            boolean success = adb.deleteAppointment(ab);

            if (success) {
                // Redirect back to the list with a success message (optional)
                response.sendRedirect("GetAppServlet?msg=deleted");
            } else {
                response.sendRedirect("GetAppServlet?error=delete_failed");
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
