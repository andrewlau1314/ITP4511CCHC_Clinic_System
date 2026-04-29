package com.cchc.controller.admin;

import com.cchc.DAO.ServiceDB;
import com.cchc.bean.ServiceBean;
import com.cchc.bean.UserBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EditServiceServlet", urlPatterns = {"/admin/editService.do"})
public class EditServiceServlet extends HttpServlet {

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

        if ("GET".equals(request.getMethod())) {
            // 顯示編輯表單
            try {
                int serviceId = Integer.parseInt(request.getParameter("serviceId"));
                ServiceBean service = serviceDB.queryServiceById(serviceId);   // 你原本已有這個方法
                if (service != null) {
                    request.setAttribute("service", service);
                    request.getRequestDispatcher("/views/admin/editService.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/manageServices.do");
                }
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/admin/manageServices.do");
            }
        } else {
            // POST：更新服務
            try {
                ServiceBean sb = new ServiceBean();
                sb.setServiceId(Integer.parseInt(request.getParameter("serviceId")));
                sb.setServiceName(request.getParameter("serviceName"));
                sb.setDescription(request.getParameter("description"));

                boolean success = serviceDB.updateService(sb);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/admin/manageServices.do");
                } else {
                    request.setAttribute("error", "❌ 更新失敗！");
                    request.getRequestDispatcher("/views/admin/editService.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "❌ 系統錯誤！");
                request.getRequestDispatcher("/views/admin/editService.jsp").forward(request, response);
            }
        }
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