package com.cchc.controller.admin;

import com.cchc.DAO.UserDB;
import com.cchc.bean.UserBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DeleteUserServlet", urlPatterns = {"/admin/deleteUser.do"})
public class DeleteUserServlet extends HttpServlet {

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

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            // 防止管理員刪除自己
            if (userId == currentUser.getUserId()) {
                response.sendRedirect(request.getContextPath() + "/admin/manageUsers.do");
                return;
            }

            // 這裡使用軟刪除（把 active 設為 0），避免真正刪除資料
            boolean success = userDB.deactivateUser(userId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/manageUsers.do");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/manageUsers.do");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manageUsers.do");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}