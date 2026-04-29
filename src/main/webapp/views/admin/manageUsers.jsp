<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="java.util.ArrayList" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ArrayList<UserBean> allUsers = (ArrayList<UserBean>) request.getAttribute("allUsers");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理用戶帳號 / Manage Users - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1300px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        table { width:100%; border-collapse:collapse; margin-top:20px; }
        th, td { padding:12px; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#343a40; color:white; }
        button { padding:8px 16px; margin:3px; border:none; border-radius:5px; cursor:pointer; }
        .edit-btn { background:#ffc107; color:black; }
        .delete-btn { background:#dc3545; color:white; }
    </style>
</head>
<body>
    <div class="container">
        <h2>👥 管理用戶帳號 / Manage User Accounts</h2>
        <p>管理員：<%= currentUser.getFullName() %></p>

        <a href="${pageContext.request.contextPath}/admin/addUser.do">
            <button>+ 新增用戶 / Add New User</button>
        </a>

        <table>
            <tr>
                <th>用戶ID</th>
                <th>帳號</th>
                <th>姓名</th>
                <th>角色</th>
                <th>Email</th>
                <th>電話</th>
                <th>狀態</th>
                <th>操作</th>
            </tr>
            <% for (UserBean u : allUsers) { %>
            <tr>
                <td><%= u.getUserId() %></td>
                <td><%= u.getUsername() %></td>
                <td><%= u.getFullName() %></td>
                <td><%= u.getRole() %></td>
                <td><%= u.getEmail() != null ? u.getEmail() : "-" %></td>
                <td><%= u.getPhone() != null ? u.getPhone() : "-" %></td>
                <td><%= u.isActive() ? "✅ 啟用" : "❌ 停用" %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/editUser.do?userId=<%= u.getUserId() %>">
                        <button class="edit-btn">編輯 / Edit</button>
                    </a>
                    <button class="delete-btn" onclick="if(confirm('確定刪除此用戶？')) location.href='${pageContext.request.contextPath}/admin/deleteUser.do?userId=<%= u.getUserId() %>'">刪除 / Delete</button>
                </td>
            </tr>
            <% } %>
        </table>

        <br>
        <a href="${pageContext.request.contextPath}/admin/dashboard.do">
            <button>返回管理員首頁 / Back to Admin Dashboard</button>
        </a>
    </div>
</body>
</html>