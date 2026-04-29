package com.cchc.controller.admin;

import com.cchc.DAO.UserDB;
import com.cchc.bean.UserBean;
import com.cchc.bean.UserBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddUserServlet", urlPatterns = {"/admin/addUser.do"})
public class AddUserServlet extends HttpServlet {

    private UserDB userDB;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        userDB = new UserDB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean currentUser = (UserBean) session.getAttribute("currentUser");

        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/views/common/login.jsp");
            return;
        }

        String method = request.getMethod();

        if ("GET".equals(method)) {
            // 顯示新增用戶表單
            request.getRequestDispatcher("/views/admin/addUser.jsp").forward(request, response);
        } else if ("POST".equals(method)) {
            // 處理表單提交
            try {
                UserBean newUser = new UserBean();
                newUser.setUsername(request.getParameter("username"));
                newUser.setPassword(request.getParameter("password"));
                newUser.setFullName(request.getParameter("fullName"));
                newUser.setEmail(request.getParameter("email"));
                newUser.setPhone(request.getParameter("phone"));
                newUser.setRole(request.getParameter("role"));

                String clinicIdStr = request.getParameter("clinicId");
                if (clinicIdStr != null && !clinicIdStr.isEmpty()) {
                    newUser.setClinicId(Integer.parseInt(clinicIdStr));
                }

                boolean success = userDB.addUser(newUser);

                if (success) {
                    request.setAttribute("message", "✅ 新增用戶成功！");
                    response.sendRedirect(request.getContextPath() + "/admin/manageUsers.do");
                } else {
                    request.setAttribute("error", "❌ 新增失敗！");
                    request.getRequestDispatcher("/views/admin/addUser.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "系統錯誤！");
                request.getRequestDispatcher("/views/admin/addUser.jsp").forward(request, response);
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