<%--
    Document : adminDashboard
    Created on : 2026年4月29日
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
    <title>管理員後台 / Admin Dashboard - CCHC</title>
    
    <style>
        body { 
            font-family: Arial; 
            margin:0; 
            padding:0; 
            background:#f4f7f6; 
        }
        .header { 
            background:#343a40; 
            color:white; 
            padding:25px; 
            text-align:center; 
        }
        .container { 
            max-width:1200px; 
            margin:30px auto; 
            padding:30px; 
            background:white; 
            border-radius:12px; 
            box-shadow:0 0 15px rgba(0,0,0,0.1); 
        }
        .menu { 
            display:flex; 
            gap:18px; 
            flex-wrap:wrap; 
            margin:25px 0; 
        }
        .menu a {
            padding:18px 28px;
            background:#007bff;
            color:white;
            text-decoration:none;
            border-radius:10px;
            font-size:17px;
            font-weight:bold;
            transition: all 0.3s;
        }
        .menu a:hover { 
            background:#0056b3; 
            transform: translateY(-3px); 
        }
        .logout {
            background:#dc3545 !important;
        }
        .logout:hover { 
            background:#c82333 !important; 
        }
    </style>
</head>
<body>
    <div class="header">
        <!-- Header -->
        <h2>🔧 CCHC 管理員後台 / CCHC Admin Dashboard</h2>
        <p>
            歡迎，<%= currentUser.getFullName() %> (管理員) / 
            Welcome, <%= currentUser.getFullName() %> (Administrator)
        </p>
    </div>
    
    <div class="container">
        <div class="menu">
            <a href="${pageContext.request.contextPath}/admin/manageUsers.do">
                👥 管理用戶帳號 / Manage User Accounts
            </a>
            <a href="${pageContext.request.contextPath}/admin/manageClinics.do">
                🏥 管理診所 / Manage Clinics
            </a>
            <a href="${pageContext.request.contextPath}/admin/manageServices.do">
                🩺 管理服務項目 / Manage Services
            </a>
            <a href="${pageContext.request.contextPath}/admin/manageClinicServices.do">
                🔗 診所服務設定 / Clinic-Service Settings
            </a>
            <a href="${pageContext.request.contextPath}/admin/allBookings.do">
                📋 查看所有預約 / View All Bookings
            </a>
            <a href="${pageContext.request.contextPath}/admin/reports.do">
                📊 系統報表 / System Reports
            </a>
            <a href="${pageContext.request.contextPath}/logout.do" class="logout">
                🚪 登出 / Logout
            </a>
        </div>
        
        <!-- Welcome Section -->
        <h3>歡迎使用管理員後台！ / Welcome to Admin Dashboard!</h3>
        <p>
            您可以管理用戶、診所、服務、預約記錄與系統統計報表。 / 
            You can manage users, clinics, services, appointment records, and system statistics reports.
        </p>
    </div>
</body>
</html>