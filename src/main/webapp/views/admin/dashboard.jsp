<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.UserBean" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理員後台 / Admin Dashboard - CCHC</title>
    <style>
        body { font-family: Arial; margin:0; padding:0; background:#f4f7f6; }
        .header { background:#343a40; color:white; padding:20px; text-align:center; }
        .container { max-width:1100px; margin:30px auto; padding:20px; background:white; border-radius:10px; box-shadow:0 0 10px rgba(0,0,0,0.1); }
        .menu { display:flex; gap:15px; flex-wrap:wrap; margin:20px 0; }
        .menu a { padding:15px 25px; background:#007bff; color:white; text-decoration:none; border-radius:8px; font-size:16px; }
        .menu a:hover { background:#0056b3; }
    </style>
</head>
<body>
    <div class="header">
        <h2>🔧 CCHC 管理員後台 / Admin Dashboard</h2>
        <p>歡迎，<%= currentUser.getFullName() %> (管理員)</p>
    </div>

    <div class="container">
        <div class="menu">
            <a href="${pageContext.request.contextPath}/admin/allBookings.do">📋 查看所有預約</a>
            <a href="${pageContext.request.contextPath}/admin/manageClinics.do">🏥 管理診所</a>
            <a href="${pageContext.request.contextPath}/admin/manageServices.do">🩺 管理服務</a>
            <a href="${pageContext.request.contextPath}/admin/manageUsers.do">👥 管理用戶</a>
            <a href="${pageContext.request.contextPath}/logout.do">🚪 登出</a>
        </div>

        <h3>歡迎使用管理員後台！</h3>
        <p>您可以管理診所、服務、用戶與預約記錄。</p>
    </div>
</body>
</html>