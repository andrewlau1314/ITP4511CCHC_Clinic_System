<%-- 
    Document   : myBookings
    Created on : 2026年4月29日, 上午7:58:27
    Author     : user
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.AppointmentBean" %>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="java.util.ArrayList" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ArrayList<AppointmentBean> myBookings = (ArrayList<AppointmentBean>) request.getAttribute("myBookings");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的預約記錄 / My Bookings - CCHC</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1200px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        table { width:100%; border-collapse:collapse; margin-top:20px; }
        th, td { padding:12px; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#007bff; color:white; }
        .status-confirmed { color:#28a745; font-weight:bold; }
        .status-pending { color:#ffc107; }
        .status-cancelled { color:#dc3545; }
        button { padding:8px 16px; margin:2px; border:none; border-radius:5px; cursor:pointer; font-size:14px; }
        .cancel-btn { background:#dc3545; color:white; }
        .reschedule-btn { background:#ffc107; color:black; }
    </style>
</head>
<body>
    <div class="container">
        <h2>📋 我的預約記錄 / My Bookings - <%= currentUser.getFullName() %></h2>

        <% if (myBookings != null && !myBookings.isEmpty()) { %>
            <table>
                <tr>
                    <th>日期 / Date</th>
                    <th>時間 / Time</th>
                    <th>診所 / Clinic</th>
                    <th>服務 / Service</th>
                    <th>狀態 / Status</th>
                    <th>取消原因 / Cancel Reason</th>
                    <th>操作 / Action</th>
                </tr>
                <% for (AppointmentBean ab : myBookings) { %>
                <tr>
                    <td><%= ab.getAppointmentDate() %></td>
                    <td><%= ab.getAppointmentTime() %></td>
                    <td><%= ab.getClinicId() %> 診所 / Clinic <%= ab.getClinicId() %></td>
                    <td><%= ab.getServiceId() %> 服務 / Service <%= ab.getServiceId() %></td>
                    <td><span class="status-<%= ab.getStatus().toLowerCase() %>"><%= ab.getStatus() %></span></td>
                    <td><%= ab.getCancelReason() != null ? ab.getCancelReason() : "-" %></td>
                    <td>
                        <% if ("PENDING".equals(ab.getStatus())) { %>
                            <form action="${pageContext.request.contextPath}/patient/cancel.do" method="post" style="display:inline;">
                                <input type="hidden" name="appointmentId" value="<%= ab.getAppointmentId() %>">
                                <button type="submit" class="cancel-btn" onclick="return confirm('確定要取消此預約嗎？ / Are you sure to cancel this appointment?')">取消 / Cancel</button>
                            </form>
                            <a href="${pageContext.request.contextPath}/patient/reschedule.do?appointmentId=<%= ab.getAppointmentId() %>">
                                <button class="reschedule-btn">改期 / Reschedule</button>
                            </a>
                        <% } else { %>
                            -
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </table>
        <% } else { %>
            <p>目前沒有任何預約記錄。 / You have no appointments yet.</p>
        <% } %>

        <br><br>
        <a href="${pageContext.request.contextPath}/patient/clinics.do">
            <button>返回預約首頁 / Back to Booking Home</button>
        </a>
    </div>
</body>
</html>