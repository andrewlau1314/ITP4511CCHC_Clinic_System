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

@WebServlet(name = "AddServiceServlet", urlPatterns = {"/admin/addService.do"})
public class AddServiceServlet extends HttpServlet {

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
            request.getRequestDispatcher("/views/admin/addService.jsp").forward(request, response);
        } else {
            try {
                ServiceBean sb = new ServiceBean();
                sb.setServiceName(request.getParameter("serviceName"));
                sb.setDescription(request.getParameter("description"));

                boolean success = serviceDB.addService(sb);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/admin/manageServices.do");
                } else {
                    request.setAttribute("error", "❌ 新增服務失敗！");
                    request.getRequestDispatcher("/views/admin/addService.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "❌ 系統錯誤！");
                request.getRequestDispatcher("/views/admin/addService.jsp").forward(request, response);
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