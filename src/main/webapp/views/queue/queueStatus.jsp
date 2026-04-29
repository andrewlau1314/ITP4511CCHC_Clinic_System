<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.UserBean" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    String message = (String) request.getAttribute("message");
    String queueNumber = (String) request.getAttribute("queueNumber");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>排隊狀態 / Queue Status - CCHC</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:40px; text-align:center; }
        .container { max-width:600px; margin:auto; background:white; padding:50px; border-radius:15px; box-shadow:0 0 20px rgba(0,0,0,0.15); }
        h1 { color:#28a745; font-size:36px; }
        .queue-number { font-size:72px; font-weight:bold; color:#007bff; margin:20px 0; letter-spacing:8px; }
        button { padding:15px 35px; font-size:18px; background:#007bff; color:white; border:none; border-radius:8px; cursor:pointer; margin:15px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🚶 您已成功加入即日排隊！</h1>
        <h2>Join Queue Successfully!</h2>
        
        <p><%= currentUser != null ? currentUser.getFullName() : "訪客 / Guest" %></p>
        
        <div class="queue-number">
            <%= queueNumber != null ? queueNumber : "Q999" %>
        </div>
        
        <p><strong>您的目前排隊號碼 / Your Queue Number</strong></p>
        
        <% if (message != null) { %>
            <p style="color:#28a745; font-size:18px;"><%= message %></p>
        <% } %>

        <a href="${pageContext.request.contextPath}/patient/clinics.do">
            <button>返回預約首頁 / Back to Booking</button>
        </a>
        
        <a href="${pageContext.request.contextPath}/patient/myBookings.do">
            <button>查看我的預約 / My Bookings</button>
        </a>
    </div>
</body>
</html>