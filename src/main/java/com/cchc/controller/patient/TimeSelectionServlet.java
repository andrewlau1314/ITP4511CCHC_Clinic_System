/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
package com.cchc.controller.patient;

import com.cchc.DAO.AppointmentDB;
import com.cchc.DAO.ClinicDB;
import com.cchc.bean.ClinicBean;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TimeSelectionServlet", urlPatterns = {"/timeslots.do"})
public class TimeSelectionServlet extends HttpServlet {

    private ClinicDB clinicDB;
    private AppointmentDB appointmentDB;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        
        clinicDB = new ClinicDB(dbUrl, dbUser, dbPassword);
        appointmentDB = new AppointmentDB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int clinicId = Integer.parseInt(request.getParameter("clinicId"));
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        LocalDate selectedDate = LocalDate.parse(request.getParameter("appointmentDate"));

        ClinicBean clinic = clinicDB.queryClinicById(clinicId);
        ArrayList<String> availableTimes = appointmentDB.getAvailableTimesForDate(clinicId, serviceId, selectedDate);

        request.setAttribute("clinic", clinic);
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("selectedDate", selectedDate);
        request.setAttribute("availableTimes", availableTimes);

        request.getRequestDispatcher("/views/patient/timeSelection.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}