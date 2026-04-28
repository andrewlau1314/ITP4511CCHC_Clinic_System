<%-- 
    Document   : timeslotList
    Created on : 2026年4月28日, 下午10:59:59
    Author     : user
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ClinicBean" %>
<%
    ClinicBean clinic = (ClinicBean) request.getAttribute("clinic");
    Integer serviceId = (Integer) request.getAttribute("serviceId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>可預約時段 - <%= clinic.getName() %></title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1000px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        .timeslot-card { border:1px solid #ddd; padding:15px; margin:12px 0; border-radius:8px; display:flex; justify-content:space-between; align-items:center; }
        button { padding:12px 25px; background:#28a745; color:white; border:none; border-radius:5px; cursor:pointer; font-size:16px; }
        button:hover { background:#218838; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🩺 <%= clinic.getName() %> - 可預約時段</h2>
        <p><strong>地址：</strong><%= clinic.getAddress() %></p>

        <h3>可預約時段</h3>

        <!-- 目前先顯示 placeholder，之後會接真實時段資料 -->
        <div class="timeslot-card">
            <div>
                <strong>2026-04-28 (今天)</strong><br>
                09:00 - 09:15
            </div>
            <div>
                <button onclick="bookAppointment(<%= serviceId %>, '2026-04-28', '09:00:00')">立即預約</button>
            </div>
        </div>

        <div class="timeslot-card">
            <div>
                <strong>2026-04-28 (今天)</strong><br>
                09:15 - 09:30
            </div>
            <div>
                <button onclick="bookAppointment(<%= serviceId %>, '2026-04-28', '09:15:00')">立即預約</button>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/patient/clinics.do">
            <button>← 返回診所列表</button>
        </a>
    </div>

    <script>
        function bookAppointment(serviceId, date, time) {
            if (confirm("確定要預約 " + date + " " + time + " 嗎？")) {
                window.location.href = "${pageContext.request.contextPath}/book.do?serviceId=" + serviceId + 
                                       "&appointmentDate=" + date + "&appointmentTime=" + time;
            }
        }
    </script>
</body>
</html>