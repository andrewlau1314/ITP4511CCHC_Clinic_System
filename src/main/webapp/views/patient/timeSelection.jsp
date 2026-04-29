<%-- 
    Document   : timeSelection
    Created on : 2026年4月29日, 上午1:30:12
    Author     : user
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ClinicBean" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.ArrayList" %>
<%
    ClinicBean clinic = (ClinicBean) request.getAttribute("clinic");
    Integer serviceId = (Integer) request.getAttribute("serviceId");
    LocalDate selectedDate = (LocalDate) request.getAttribute("selectedDate");
    ArrayList<String> availableTimes = (ArrayList<String>) request.getAttribute("availableTimes");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>選擇時間 - <%= clinic.getName() %></title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:900px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        .time-btn { padding:18px 35px; margin:10px; background:#28a745; color:white; border:none; border-radius:10px; cursor:pointer; font-size:17px; }
        .time-btn:hover { background:#218838; }
        .no-time { color:#dc3545; font-size:20px; padding:30px; text-align:center; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🩺 <%= clinic.getName() %> - 選擇時間</h2>
        <p><strong>日期：</strong><%= selectedDate %></p>

        <h3>可用時間段</h3>

        <% if (availableTimes != null && !availableTimes.isEmpty()) { 
            for (String time : availableTimes) { %>
                <form action="${pageContext.request.contextPath}/book.do" method="post" style="display:inline-block;">
                    <input type="hidden" name="clinicId" value="<%= clinic.getClinicId() %>">
                    <input type="hidden" name="serviceId" value="<%= serviceId %>">
                    <input type="hidden" name="appointmentDate" value="<%= selectedDate %>">
                    <input type="hidden" name="appointmentTime" value="<%= time %>">
                    <button type="submit" class="time-btn"><%= time %></button>
                </form>
        <%   } 
           } else { %>
            <p class="no-time">❌ 這個日期目前沒有可預約的時間</p>
        <% } %>

        <br><br>
        <a href="${pageContext.request.contextPath}/services.do?clinicId=<%= clinic.getClinicId() %>">
            ← 重新選擇日期
        </a>
    </div>
</body>
</html>