<%--
    Document : dashboard
    Created on : 2026年4月22日, 下午10:24:31
    Author   : user
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
    <!-- Title -->
    <title>病人主頁 - CCHC | Patient Dashboard - CCHC</title>
    
    <style>
        body { 
            font-family: Arial; 
            margin:0; 
            padding:0; 
            background:#f4f7f6; 
        }
        .header { 
            background:#007bff; 
            color:white; 
            padding:15px; 
            text-align:center; 
        }
        .container { 
            max-width:1000px; 
            margin:30px auto; 
            padding:20px; 
            background:white; 
            border-radius:10px; 
            box-shadow:0 0 10px rgba(0,0,0,0.1); 
        }
        .menu { 
            display:flex; 
            gap:15px; 
            margin:20px 0; 
            flex-wrap: wrap;
        }
        .menu a { 
            padding:12px 20px; 
            background:#007bff; 
            color:white; 
            text-decoration:none; 
            border-radius:5px; 
            white-space: nowrap;
        }
        .menu a:hover { background:#0056b3; }
    </style>
</head>
<body>
    <div class="header">
        <!-- Header -->
        <h2>🩺 CCHC 社區診所系統 - 病人專區 | CCHC Community Clinic System - Patient Area</h2>
        <p>
            歡迎，<%= currentUser.getFullName() %> (<%= currentUser.getRole() %>) | 
            Welcome, <%= currentUser.getFullName() %> (<%= currentUser.getRole() %>)
        </p>
    </div>
   
    <!-- Welcome Section -->
    <h3>歡迎使用系統！ | Welcome to the System!</h3>
    <p>
        你可以開始預約診症、查看排隊狀況，或管理個人資料。 | 
        You can start booking appointments, check queue status, or manage your personal information.
    </p>
  
    <div class="menu">
        <a href="${pageContext.request.contextPath}/patient/clinics.do">
            📅 預約診症 | Book Appointment
        </a>
        
        <a href="${pageContext.request.contextPath}/patient/myBookings.do">
            📋 我的預約 | My Bookings
        </a>
        
        <!-- doing -->
        <a href="${pageContext.request.contextPath}/queue/join.do">
            🚶 加入即日排隊 | Join Same-Day Queue
        </a>
        
        <a href="${pageContext.request.contextPath}/patient/queueStatus.do">
            👀 查看排隊狀態 | View My Queue Status
        </a>
        
        <a href="${pageContext.request.contextPath}/patient/queueAllStatus.do">
            👀 各診所排隊狀態 | All Clinics Queue Status
        </a>
        
        <!-- Still not done -->
        <a href="${pageContext.request.contextPath}/patient/notifications.do">
            🛎 我的通知 | My Notifications
        </a>
        
        <a href="${pageContext.request.contextPath}/logout.do">
            🚪 登出 | Logout
        </a>
    </div>
       
</body>
</html>