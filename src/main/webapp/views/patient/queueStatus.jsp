<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.UserBean" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    Integer currentNumber = (Integer) request.getAttribute("currentNumber");
    Integer nextNumber    = (Integer) request.getAttribute("nextNumber");
    String myQueueNumber  = (String) request.getAttribute("myQueueNumber");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>排隊狀態 / Queue Status - CCHC</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:700px; margin:auto; background:white; padding:40px; border-radius:15px; box-shadow:0 0 20px rgba(0,0,0,0.15); text-align:center; }
        h1 { color:#28a745; }
        .number-box { font-size:60px; font-weight:bold; background:#007bff; color:white; padding:20px; border-radius:12px; margin:20px 0; }
        .my-number { background:#ffc107; color:black; }
        p { font-size:18px; margin:15px 0; }
        button { padding:15px 30px; font-size:18px; margin:10px; border:none; border-radius:8px; cursor:pointer; }
    </style>
</head>
<body>
    <div class="container">
        <h1>👀 目前排隊狀態 / Current Queue Status</h1>
        <p>歡迎，<%= currentUser.getFullName() %> / Welcome, <%= currentUser.getFullName() %></p>

        <h3>當前叫號 / Current Calling Number</h3>
        <div class="number-box">
            <%= currentNumber != null ? "Q" + String.format("%03d", currentNumber) : "目前無叫號" %>
        </div>

        <h3>下一個號碼 / Next Number</h3>
        <div class="number-box">
            <%= nextNumber != null ? "Q" + String.format("%03d", nextNumber) : "目前無下一個號碼" %>
        </div>

        <h3>您的號碼 / Your Queue Number</h3>
        <div class="number-box my-number">
            <%= myQueueNumber != null ? myQueueNumber : "您尚未排隊" %>
        </div>

        <a href="${pageContext.request.contextPath}/queue/join.do">
            <button>🔄 重新加入排隊 / Join Queue Again</button>
        </a>
        <a href="${pageContext.request.contextPath}/patient/clinics.do">
            <button>返回預約首頁 / Back to Booking</button>
        </a>
    </div>
</body>
</html>