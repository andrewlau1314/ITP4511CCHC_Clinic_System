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
    <title>管理診所 / Manage Clinics - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1300px; margin:auto; background:white; padding:30px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        table { width:100%; border-collapse:collapse; margin-top:20px; }
        th, td { padding:14px; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#343a40; color:white; }
        button { padding:8px 16px; margin:3px; border:none; border-radius:5px; cursor:pointer; }
        .edit-btn { background:#ffc107; color:black; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🏥 管理診所 / Manage Clinics</h2>
        <p>管理員：<%= currentUser.getFullName() %></p>

        <a href="${pageContext.request.contextPath}/admin/addClinic.do">
            <button>+ 新增診所 / Add New Clinic</button>
        </a>

        <table>
            <tr>
                <th>診所ID</th>
                <th>診所名稱 / Name</th>
                <th>地址 / Address</th>
                <th>電話 / Phone</th>
                <th>操作 / Actions</th>
            </tr>
            <% for (ClinicBean c : clinics) { %>
            <tr>
                <td><%= c.getClinicId() %></td>
                <td><%= c.getName() %></td>
                <td><%= c.getAddress() != null ? c.getAddress() : "-" %></td>
                <td><%= c.getPhone() != null ? c.getPhone() : "-" %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/editClinic.do?clinicId=<%= c.getClinicId() %>">
                        <button class="edit-btn">編輯 / Edit</button>
                    </a>
                </td>
            </tr>
            <% } %>
        </table>

        <br>
        <a href="${pageContext.request.contextPath}/admin/dashboard.do">
            <button>← 返回管理員首頁 / Back to Admin Dashboard</button>
        </a>
    </div>
</body>
</html>