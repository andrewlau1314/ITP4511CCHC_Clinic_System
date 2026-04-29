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

@WebServlet(name = "EditUserServlet", urlPatterns = {"/admin/editUser.do"})
public class EditUserServlet extends HttpServlet {

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

        if ("GET".equals(request.getMethod())) {
            // 顯示編輯表單
            try {
                int userId = Integer.parseInt(request.getParameter("userId"));
                UserBean userToEdit = userDB.getUserById(userId);
                if (userToEdit != null) {
                    request.setAttribute("userToEdit", userToEdit);
                    request.getRequestDispatcher("/views/admin/editUser.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/manageUsers.do");
                }
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/admin/manageUsers.do");
            }
        } else {
            // POST：更新資料
            try {
                UserBean ub = new UserBean();
                ub.setUserId(Integer.parseInt(request.getParameter("userId")));
                ub.setFullName(request.getParameter("fullName"));
                ub.setEmail(request.getParameter("email"));
                ub.setPhone(request.getParameter("phone"));
                ub.setRole(request.getParameter("role"));

                String clinicIdStr = request.getParameter("clinicId");
                if (clinicIdStr != null && !clinicIdStr.trim().isEmpty()) {
                    ub.setClinicId(Integer.parseInt(clinicIdStr));
                } else {
                    ub.setClinicId(0);
                }

                ub.setActive("1".equals(request.getParameter("active")));

                boolean success = userDB.updateUser(ub);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/admin/manageUsers.do");
                } else {
                    request.setAttribute("error", "❌ 更新失敗！");
                    request.getRequestDispatcher("/views/admin/editUser.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "❌ 系統錯誤！");
                request.getRequestDispatcher("/views/admin/editUser.jsp").forward(request, response);
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