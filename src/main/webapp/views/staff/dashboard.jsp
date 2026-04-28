<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="com.cchc.bean.ClinicBean" %>

<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ClinicBean currentClinic = (ClinicBean) session.getAttribute("currentClinic");

    if (currentUser == null || !"STAFF".equals(currentUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Staff Dashboard - CCHC</title>
        <style>
            body {
                font-family: 'Segoe UI', Arial;
                margin:0;
                padding:0;
                background:#f4f7f6;
            } /* [cite: 3] */
            .header {
                background:#28a745;
                color:white;
                padding:15px;
                text-align:center;
            } /* [cite: 4] */
            .container {
                max-width:1100px;
                margin:30px auto;
                padding:20px;
                background:white;
                border-radius:10px;
                box-shadow:0 0 10px rgba(0,0,0,0.1);
            } /* [cite: 5] */
            .menu {
                display:flex;
                gap:15px;
                margin:20px 0;
                flex-wrap:wrap;
                background: #e9ecef;
                padding: 15px;
                border-radius: 5px;
            } /* [cite: 6] */
            .menu a {
                padding:12px 20px;
                background:#28a745;
                color:white;
                text-decoration:none;
                border-radius:5px;
                font-weight: bold;
            } /* [cite: 7] */
            .menu a:hover {
                background:#218838;
            }
            .content-area {
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 5px;
                min-height: 400px;
            }
            .welcome-msg {
                color: #666;
                font-style: italic;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <h1>🩺 CCHC 社區診所管理系統 - <%= currentClinic.getName()%></h1>
            <h2>職員：<%= currentUser.getFullName()%> </h2>
        </div>

        <div class="container">
            <div class="menu">
                <a href="GetAppServlet">📅 預約管理</a>
                <a href="dashboard.jsp?page=checkin">✅ 病人報到</a>
                <a href="dashboard.jsp?page=queueControl">🚶 叫號管理 (Extra)</a>
                <a href="dashboard.jsp?page=serviceQuota">📊 服務名額設定</a>
                <a href="../../logout.do" style="background:#dc3545;">🚪 登出</a>
            </div>

            <%
                String target = request.getParameter("page");
                if (target == null || target.isEmpty()) {
            %>
            <h3>今日工作概要</h3>
            <p class="welcome-msg">歡迎回來！請從上方選單選擇要執行的操作。</p>
            <%
            } else {
                String includePage = "";
                if ("appointmentList".equals(target)) {
                    includePage = "./appointment_list.jsp";
                } else if ("checkin".equals(target)) {
                    includePage = "patient_checkin.jsp";
                } else if ("queueControl".equals(target)) {
                    includePage = "queue_management.jsp";
                } else if ("serviceQuota".equals(target)) {
                    includePage = "manage_quota.jsp";
                } else {
                    includePage = "error.jsp";
                }
            %>
            <jsp:include page="<%= includePage%>" />
            <%
                }
            %>
        </div>
    </div>
</body>
</html>