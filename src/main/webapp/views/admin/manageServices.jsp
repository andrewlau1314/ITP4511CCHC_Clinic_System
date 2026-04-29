<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ServiceBean" %>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="java.util.ArrayList" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ArrayList<ServiceBean> services = (ArrayList<ServiceBean>) request.getAttribute("services");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理服務 / Manage Services - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1200px; margin:auto; background:white; padding:30px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        table { width:100%; border-collapse:collapse; margin-top:20px; }
        th, td { padding:14px; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#343a40; color:white; }
        button { padding:8px 16px; margin:3px; border:none; border-radius:5px; cursor:pointer; }
        .edit-btn { background:#ffc107; color:black; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🩺 管理服務 / Manage Services</h2>
        <p>管理員：<%= currentUser.getFullName() %></p>

        <a href="${pageContext.request.contextPath}/admin/addService.do">
            <button>+ 新增服務 / Add New Service</button>
        </a>

        <table>
            <tr>
                <th>服務ID</th>
                <th>服務名稱 / Service Name</th>
                <th>描述 / Description</th>
                <th>操作 / Actions</th>
            </tr>
            <% for (ServiceBean s : services) { %>
            <tr>
                <td><%= s.getServiceId() %></td>
                <td><%= s.getServiceName() %></td>
                <td><%= s.getDescription() != null ? s.getDescription() : "-" %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/editService.do?serviceId=<%= s.getServiceId() %>">
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