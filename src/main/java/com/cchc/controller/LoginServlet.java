/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.controller;

import com.cchc.DAO.ClinicDB;
import com.cchc.DAO.UserDB;
import com.cchc.bean.ClinicBean;
import com.cchc.bean.UserBean;
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

    private UserDB udb;
    private ClinicDB cdb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        udb = new UserDB(dbUrl, dbUser, dbPassword);
        cdb = new ClinicDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserBean user = udb.login(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            session.setMaxInactiveInterval(30 * 60); // Session 逾時

            String contextPath = request.getContextPath();

            // 修正後的正確路徑
            if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect(contextPath + "/views/admin/dashboard.jsp");

            } else if ("STAFF".equals(user.getRole())) {
                ClinicBean clinic = cdb.queryClinicById(user.getClinicId());
                session.setAttribute("currentClinic", clinic);

                response.sendRedirect(contextPath + "/views/staff/dashboard.jsp");

            } else if ("PATIENT".equals(user.getRole())) {
                response.sendRedirect(contextPath + "/views/patient/dashboard.jsp");

            }
        } else {
            request.setAttribute("error", "Username or Password Incorrect.！");
            request.getRequestDispatcher("/views/common/login.jsp").forward(request, response);
        }
    }
}
