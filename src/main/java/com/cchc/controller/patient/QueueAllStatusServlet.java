package com.cchc.controller.patient;

import com.cchc.DAO.QueueDB;
import com.cchc.bean.QueueBean;
import com.cchc.bean.UserBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "QueueAllStatusServlet", urlPatterns = {"/patient/queueAllStatus.do"})
public class QueueAllStatusServlet extends HttpServlet {

    private QueueDB queueDB;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        queueDB = new QueueDB(dbUrl, dbUser, dbPassword);
    }

       protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean currentUser = (UserBean) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/views/common/login.jsp");
            return;
        }

        ArrayList<QueueBean> allQueueStatus = queueDB.getAllClinicsQueueStatus();

        // 為每個診所檢查用戶是否有排隊
        for (QueueBean qb : allQueueStatus) {
            String myNumber = queueDB.getMyQueueNumberInClinic(currentUser.getUserId(), qb.getClinicId());
            if (myNumber != null) {
                qb.setQueueNumber(myNumber);   // 把自己的號碼存進 bean
            }
        }

        request.setAttribute("allQueueStatus", allQueueStatus);
        request.getRequestDispatcher("/views/patient/queueAllStatus.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}