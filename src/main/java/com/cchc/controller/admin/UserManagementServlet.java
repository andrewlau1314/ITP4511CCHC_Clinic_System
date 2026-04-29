package com.cchc.controller.admin;

import com.cchc.DAO.UserDB;           // ← 改成 UserDB
import com.cchc.bean.UserBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UserManagementServlet", urlPatterns = {"/admin/manageUsers.do"})
public class UserManagementServlet extends HttpServlet {

    private UserDB userDB;               // ← 改成 UserDB

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        userDB = new UserDB(dbUrl, dbUser, dbPassword);   // ← 改成 UserDB
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean currentUser = (UserBean) session.getAttribute("currentUser");

        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/views/common/login.jsp");
            return;
        }

        ArrayList<UserBean> allUsers = userDB.getAllUsers();   // ← 使用 UserDB

        request.setAttribute("allUsers", allUsers);
        request.getRequestDispatcher("/views/admin/manageUsers.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}