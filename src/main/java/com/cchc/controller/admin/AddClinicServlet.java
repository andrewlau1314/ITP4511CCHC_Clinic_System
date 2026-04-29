package com.cchc.controller.admin;

import com.cchc.DAO.ClinicDB;
import com.cchc.bean.ClinicBean;
import com.cchc.bean.UserBean;
import java.io.IOException;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddClinicServlet", urlPatterns = {"/admin/addClinic.do"})
public class AddClinicServlet extends HttpServlet {

    private ClinicDB clinicDB;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        clinicDB = new ClinicDB(dbUrl, dbUser, dbPassword);
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
            // 顯示新增表單
            request.getRequestDispatcher("/views/admin/addClinic.jsp").forward(request, response);
                } else {
            // POST：處理新增
            try {
                ClinicBean cb = new ClinicBean();
                cb.setName(request.getParameter("name"));
                cb.setAddress(request.getParameter("address"));
                cb.setPhone(request.getParameter("phone"));
                cb.setDayOff(request.getParameter("dayOff"));

                // ✅ 修正：使用 LocalTime.parse（不需要 + ":00"）
                cb.setLunchBreakStart(LocalTime.parse(request.getParameter("lunchBreakStart")));
                cb.setLunchBreakEnd(LocalTime.parse(request.getParameter("lunchBreakEnd")));
                cb.setOpenTime(LocalTime.parse(request.getParameter("openTime")));
                cb.setCloseTime(LocalTime.parse(request.getParameter("closeTime")));

                boolean success = clinicDB.addClinic(cb);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/admin/manageClinics.do");
                } else {
                    request.setAttribute("error", "❌ 新增診所失敗！");
                    request.getRequestDispatcher("/views/admin/addClinic.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "❌ 系統錯誤！");
                request.getRequestDispatcher("/views/admin/addClinic.jsp").forward(request, response);
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