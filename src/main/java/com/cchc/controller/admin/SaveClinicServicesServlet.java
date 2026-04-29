package com.cchc.controller.admin;

import com.cchc.DAO.ServiceDB;
import com.cchc.bean.UserBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SaveClinicServicesServlet", urlPatterns = {"/admin/saveClinicServices.do"})
public class SaveClinicServicesServlet extends HttpServlet {

    private ServiceDB serviceDB;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
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

        try {
            int clinicId = Integer.parseInt(request.getParameter("clinicId"));
            
            // 先刪除該診所舊的服務關聯
            serviceDB.deleteClinicServices(clinicId);

            // 取得所有勾選的 serviceId
            String[] serviceIds = request.getParameterValues("serviceId");

            if (serviceIds != null) {
                for (String sid : serviceIds) {
                    int serviceId = Integer.parseInt(sid);
                    String quotaStr = request.getParameter("quota_" + serviceId);
                    int quota = (quotaStr != null && !quotaStr.isEmpty()) ? Integer.parseInt(quotaStr) : 10;

                    serviceDB.addClinicService(clinicId, serviceId, quota);
                }
            }

            response.sendRedirect(request.getContextPath() + "/admin/manageClinicServices.do");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manageClinicServices.do");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}