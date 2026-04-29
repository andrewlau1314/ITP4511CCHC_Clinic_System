package com.cchc.controller.patient;

import com.cchc.DAO.ClinicDB;
import com.cchc.DAO.QueueDB;
import com.cchc.DAO.ServiceDB;
import com.cchc.bean.ClinicBean;
import com.cchc.bean.ServiceBean;
import com.cchc.bean.UserBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "JoinQueueServlet", urlPatterns = {"/queue/join.do"})
public class JoinQueueServlet extends HttpServlet {

    private ClinicDB clinicDB;
    private ServiceDB serviceDB;
    private QueueDB queueDB;

    @Override
    public void init() {
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        
        clinicDB = new ClinicDB(dbUrl, dbUser, dbPassword);
        serviceDB = new ServiceDB(dbUrl, dbUser, dbPassword);
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

        // GET → 顯示表單
        if ("GET".equals(request.getMethod())) {
            ArrayList<ClinicBean> clinics = clinicDB.getClinics();
            ArrayList<ServiceBean> services = serviceDB.queryServiceByClinicId(1); // 可改成全部服務

            request.setAttribute("clinics", clinics);
            request.setAttribute("services", services);

            request.getRequestDispatcher("/views/queue/join.jsp").forward(request, response);
            return;
        }

              // POST 請求 → 執行加入排隊
        try {
            int clinicId = Integer.parseInt(request.getParameter("clinicId"));
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));

            String queueNumber = queueDB.joinQueue(currentUser.getUserId(), clinicId, serviceId);

            if (queueNumber != null) {
                request.setAttribute("message", "🎉 您已成功加入即日排隊！");
                request.setAttribute("queueNumber", queueNumber);   // 傳真實號碼
                request.getRequestDispatcher("/views/queue/queueStatus.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "❌ 加入排隊失敗，請稍後再試！");
                request.getRequestDispatcher("/views/queue/join.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "系統錯誤，請稍後再試！");
            request.getRequestDispatcher("/views/queue/join.jsp").forward(request, response);
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