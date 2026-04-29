package com.cchc.controller.patient;

import com.cchc.DAO.QueueDB;
import com.cchc.bean.UserBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "QueueStatusServlet", urlPatterns = {"/patient/queueStatus.do"})
public class QueueStatusServlet extends HttpServlet {

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

        // 取得目前排隊資訊
        int currentNumber = queueDB.getCurrentCallingNumber();   // 當前叫號
        int nextNumber = currentNumber + 1;                      // 下一個號碼
        String myQueueNumber = queueDB.getMyQueueNumber(currentUser.getUserId()); // 自己的號碼

        request.setAttribute("currentNumber", currentNumber);
        request.setAttribute("nextNumber", nextNumber);
        request.setAttribute("myQueueNumber", myQueueNumber);

        request.getRequestDispatcher("/views/patient/queueStatus.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}