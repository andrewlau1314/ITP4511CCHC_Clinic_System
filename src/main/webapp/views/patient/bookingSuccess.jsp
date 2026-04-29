<%--
    Document : bookingSuccess
    Created on : 2026年4月29日, 上午2:09:33
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
    <title>預約成功 - CCHC | Booking Successful - CCHC</title>
    
    <style>
        body { 
            font-family: Arial; 
            background:#f4f7f6; 
            margin:0; 
            padding:40px; 
            text-align:center; 
        }
        .success-box { 
            max-width:600px; 
            margin:80px auto; 
            background:white; 
            padding:50px; 
            border-radius:15px; 
            box-shadow:0 0 20px rgba(0,0,0,0.15); 
        }
        h1 { color:#28a745; font-size:42px; }
        p { font-size:18px; margin:20px 0; }
        button { 
            padding:15px 35px; 
            font-size:18px; 
            background:#007bff; 
            color:white; 
            border:none; 
            border-radius:8px; 
            cursor:pointer; 
            margin:10px; 
        }
    </style>
</head>
<body>
    <div class="success-box">
        <!-- Success Header -->
        <h1>🎉 預約成功！ | Booking Successful!</h1>
        
        <p>您的預約已經登記完成。 | Your appointment has been successfully registered.</p>
        
        <p>
            <strong><%= currentUser.getFullName() %></strong>，請記得準時到診所報到。
            <br>
            <strong><%= currentUser.getFullName() %></strong>, please remember to arrive at the clinic on time.
        </p>
        
        <!-- Buttons -->
        <a href="${pageContext.request.contextPath}/patient/clinics.do">
            <button>繼續預約其他時段 | Book Another Appointment</button>
        </a>
        
        <a href="${pageContext.request.contextPath}/patient/myBookings.do">
            <button>查看我的預約記錄 | View My Bookings</button>
        </a>
    </div>
</body>
</html>