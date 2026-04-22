/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.controller;

import com.cchc.dao.UserDAO;
import com.cchc.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 *
 * @author user
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.login(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            session.setMaxInactiveInterval(30 * 60); // 30分鐘

            String contextPath = request.getContextPath();

            // 修正後的正確路徑
            if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect(contextPath + "/views/admin/dashboard.jsp");
            } else if ("STAFF".equals(user.getRole())) {
                response.sendRedirect(contextPath + "/views/staff/dashboard.jsp");
            } else if ("PATIENT".equals(user.getRole())) {
                response.sendRedirect(contextPath + "/views/patient/dashboard.jsp");
            }
        } else {
            request.setAttribute("error", "用戶名稱或密碼錯誤！");
            request.getRequestDispatcher("/views/common/login.jsp").forward(request, response);
        }
    }
}