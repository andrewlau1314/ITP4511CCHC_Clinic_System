<%-- 
    Document   : dashboard
    Created on : 2026年4月22日, 下午10:24:31
    Author     : user
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.UserBean" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>病人主頁 - CCHC</title>
    <style>
        body { font-family: Arial; margin:0; padding:0; background:#f4f7f6; }
        .header { background:#007bff; color:white; padding:15px; text-align:center; }
        .container { max-width:1000px; margin:30px auto; padding:20px; background:white; border-radius:10px; box-shadow:0 0 10px rgba(0,0,0,0.1); }
        .menu { display:flex; gap:15px; margin:20px 0; }
        .menu a { padding:12px 20px; background:#007bff; color:white; text-decoration:none; border-radius:5px; }
        .menu a:hover { background:#0056b3; }
    </style>
</head>
<body>
    <div class="header">
        <h2>🩺 CCHC 社區診所系統 - 病人專區</h2>
        <p>歡迎，<%= currentUser.getFullName() %> (<%= currentUser.getRole() %>)</p>
    </div>
    
   <div class="menu">
    <a href="${pageContext.request.contextPath}/patient/clinics.do">📅 預約診症</a>
    <a href="${pageContext.request.contextPath}/patient/myBookings.do">📋 我的預約</a>
    <!-- Still not done -->
    <a href="${pageContext.request.contextPath}/queue/join.jsp">🚶 加入即日排隊</a>
    <a href="${pageContext.request.contextPath}/patient/notifications.jsp">🛎 我的通知</a>
    <a href="${pageContext.request.contextPath}/logout.do">🚪 登出</a>
</div>
        
        <h3>歡迎使用系統！</h3>
        <p>你可以開始預約診症、查看排隊狀況，或管理個人資料。</p>
    </div>
</body>
</html>