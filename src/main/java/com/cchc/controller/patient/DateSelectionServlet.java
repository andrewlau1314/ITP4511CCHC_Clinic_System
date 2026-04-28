/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.controller.patient;

import com.cchc.DAO.ClinicDB;
import com.cchc.bean.ClinicBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name = "DateSelectionServlet", urlPatterns = {"/dateSelection.do"})
public class DateSelectionServlet extends HttpServlet {

    private ClinicDB clinicDB;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        
        clinicDB = new ClinicDB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int clinicId = Integer.parseInt(request.getParameter("clinicId"));
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));

        ClinicBean clinic = clinicDB.queryClinicById(clinicId);

        request.setAttribute("clinic", clinic);
        request.setAttribute("serviceId", serviceId);

        request.getRequestDispatcher("/views/patient/dateSelection.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}