package com.cchc.controller.admin;

import com.cchc.DAO.ClinicDB;
import com.cchc.DAO.ServiceDB;
import com.cchc.bean.ClinicBean;
import com.cchc.bean.ServiceBean;
import com.cchc.bean.UserBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ManageClinicServicesServlet", urlPatterns = {"/admin/manageClinicServices.do"})
public class ManageClinicServicesServlet extends HttpServlet {

    private ClinicDB clinicDB;
    private ServiceDB serviceDB;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        clinicDB = new ClinicDB(dbUrl, dbUser, dbPassword);
        serviceDB = new ServiceDB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean currentUser = (UserBean) session.getAttribute("currentUser");

        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/views/common/login.jsp");
            return;
        }

        ArrayList<ClinicBean> clinics = clinicDB.getClinics();
        ArrayList<ServiceBean> allServices = serviceDB.getAllServices();

        request.setAttribute("clinics", clinics);
        request.setAttribute("allServices", allServices);
        request.getRequestDispatcher("/views/admin/manageClinicServices.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}