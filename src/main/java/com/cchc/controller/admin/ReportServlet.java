package com.cchc.controller.admin;

import com.cchc.DAO.AppointmentDB;
import com.cchc.bean.UserBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ReportServlet", urlPatterns = {"/admin/reports.do"})
public class ReportServlet extends HttpServlet {

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

        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/views/common/login.jsp");
            return;
        }

        // 基本統計
        int totalBookings = adb.getTotalBookings();
        int pendingBookings = adb.getBookingsByStatus("PENDING");
        int confirmedBookings = adb.getBookingsByStatus("CONFIRMED");
        int cancelledBookings = adb.getBookingsByStatus("CANCELLED");
        int noShowBookings = adb.getBookingsByStatus("NO_SHOW");

        // 各診所統計
        ArrayList<Map<String, Object>> clinicStats = adb.getBookingsByClinic();

        request.setAttribute("totalBookings", totalBookings);
        request.setAttribute("pendingBookings", pendingBookings);
        request.setAttribute("confirmedBookings", confirmedBookings);
        request.setAttribute("cancelledBookings", cancelledBookings);
        request.setAttribute("noShowBookings", noShowBookings);
        request.setAttribute("clinicStats", clinicStats);

        request.getRequestDispatcher("/views/admin/reports.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
