<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.AppointmentBean" %>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="java.util.ArrayList" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ArrayList<AppointmentBean> allBookings = (ArrayList<AppointmentBean>) request.getAttribute("allBookings");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>所有預約記錄 / All Bookings - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1300px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        table { width:100%; border-collapse:collapse; margin-top:20px; }
        th, td { padding:12px; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#343a40; color:white; }
        .status-confirmed { color:#28a745; }
        .status-pending { color:#ffc107; }
        .status-cancelled { color:#dc3545; }
    </style>
</head>
<body>
    <div class="container">
        <h2>📋 所有預約記錄 / All Bookings</h2>
        <p>管理員：<%= currentUser.getFullName() %></p>

        <table>
            <tr>
                <th>預約ID</th>
                <th>病人ID</th>
                <th>診所ID</th>
                <th>服務ID</th>
                <th>日期</th>
                <th>時間</th>
                <th>狀態</th>
                <th>取消原因</th>
            </tr>
            <% for (AppointmentBean ab : allBookings) { %>
            <tr>
                <td><%= ab.getAppointmentId() %></td>
                <td><%= ab.getUserId() %></td>
                <td><%= ab.getClinicId() %></td>
                <td><%= ab.getServiceId() %></td>
                <td><%= ab.getAppointmentDate() %></td>
                <td><%= ab.getAppointmentTime() %></td>
                <td><span class="status-<%= ab.getStatus().toLowerCase() %>"><%= ab.getStatus() %></span></td>
                <td><%= ab.getCancelReason() != null ? ab.getCancelReason() : "-" %></td>
            </tr>
            <% } %>
        </table>

        <br>
        <a href="${pageContext.request.contextPath}/admin/dashboard.do">
            <button>返回管理員首頁 / Back to Admin Dashboard</button>
        </a>
    </div>
</body>
</html>