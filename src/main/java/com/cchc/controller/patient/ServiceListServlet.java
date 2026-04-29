package com.cchc.controller.patient;

import com.cchc.DAO.ClinicDB;
import com.cchc.DAO.ServiceDB;
import com.cchc.bean.ClinicBean;
import com.cchc.bean.ServiceBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServiceListServlet", urlPatterns = {"/services.do"})
public class ServiceListServlet extends HttpServlet {

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

        // 只接收 clinicId
        int clinicId = Integer.parseInt(request.getParameter("clinicId"));

        ClinicBean clinic = clinicDB.queryClinicById(clinicId);
        ArrayList<ServiceBean> services = serviceDB.queryServiceByClinicId(clinicId);

        request.setAttribute("clinic", clinic);
        request.setAttribute("services", services);

        // 跳轉到日期選擇頁面（新邏輯）
        request.getRequestDispatcher("/views/patient/serviceList.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}