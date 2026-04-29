<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ClinicBean" %>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="java.util.ArrayList" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ArrayList<ClinicBean> clinics = (ArrayList<ClinicBean>) request.getAttribute("clinics");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>診所服務設定 / Clinic Services - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1200px; margin:auto; background:white; padding:30px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        table { width:100%; border-collapse:collapse; margin-top:20px; }
        th, td { padding:14px; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#343a40; color:white; }
        button { padding:10px 20px; margin:5px; border:none; border-radius:6px; cursor:pointer; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🔗 診所服務設定 / Clinic Services & Quota</h2>
        <p>管理員：<%= currentUser.getFullName() %></p>

        <table>
            <tr>
                <th>診所ID</th>
                <th>診所名稱 / Clinic Name</th>
                <th width="200px">操作 / Action</th>
            </tr>
            <% for (ClinicBean c : clinics) { %>
            <tr>
                <td><%= c.getClinicId() %></td>
                <td><strong><%= c.getName() %></strong></td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/editClinicServices.do?clinicId=<%= c.getClinicId() %>">
                        <button>設定服務與名額</button>
                    </a>
                </td>
            </tr>
            <% } %>
        </table>

        <br><br>
        <a href="${pageContext.request.contextPath}/admin/dashboard.do">
            <button>← 返回管理員首頁 / Back to Admin Dashboard</button>
        </a>
    </div>
</body>
</html>