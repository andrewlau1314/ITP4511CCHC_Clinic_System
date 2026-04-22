<%-- 
    Document   : dashboard
    Created on : 2026年4月22日, 下午10:24:31
    Author     : user
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.model.User" %>
<%
    User currentUser = (User) session.getAttribute("currentUser");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>職員主頁 - CCHC</title>
    <style>
        body { font-family: Arial; margin:0; padding:0; background:#f4f7f6; }
        .header { background:#28a745; color:white; padding:15px; text-align:center; }
        .container { max-width:1000px; margin:30px auto; padding:20px; background:white; border-radius:10px; box-shadow:0 0 10px rgba(0,0,0,0.1); }
        .menu { display:flex; gap:15px; margin:20px 0; flex-wrap:wrap; }
        .menu a { padding:12px 20px; background:#28a745; color:white; text-decoration:none; border-radius:5px; }
    </style>
</head>
<body>
    <div class="header">
        <h2>🩺 CCHC 社區診所系統 - 職員專區</h2>
        <p>歡迎，<%= currentUser.getFullName() %> (<%= currentUser.getRole() %>)</p>
    </div>
    
    <div class="container">
        <div class="menu">
            <a href="../appointment/dailyList.jsp">📅 今日預約名單</a>
            <a href="../queue/manage.jsp">🚶 管理即日排隊</a>
            <a href="../staff/checkin.jsp">✅ 病人報到 / 完成</a>
            <a href="../../logout.do">🚪 登出</a>
        </div>
        
        <h3>今日工作</h3>
        <p>請處理預約及排隊管理。</p>
    </div>
</body>
</html>