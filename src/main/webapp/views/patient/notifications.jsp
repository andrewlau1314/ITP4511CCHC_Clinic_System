<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.NotificationBean" %>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="java.util.ArrayList" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ArrayList<NotificationBean> notifications = (ArrayList<NotificationBean>) request.getAttribute("notifications");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的通知 / My Notifications - CCHC</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1000px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        table { width:100%; border-collapse:collapse; margin-top:20px; }
        th, td { padding:15px; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#007bff; color:white; }
        .unread { background:#fff3cd; }
        .read { background:white; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🛎 我的通知 / My Notifications - <%= currentUser.getFullName() %></h2>

        <% if (notifications != null && !notifications.isEmpty()) { %>
            <table>
                <tr>
                    <th>時間 / Time</th>
                    <th>類型 / Type</th>
                    <th>標題 / Title</th>
                    <th>內容 / Message</th>
                    <th>狀態 / Status</th>
                </tr>
                <% for (NotificationBean n : notifications) { %>
                <tr class="<%= n.isRead() ? "read" : "unread" %>">
                    <td><%= n.getCreatedAt() %></td>
                    <td><%= n.getType() %></td>
                    <td><%= n.getTitle() %></td>
                    <td><%= n.getMessage() %></td>
                    <td><%= n.isRead() ? "已讀 / Read" : "未讀 / Unread" %></td>
                </tr>
                <% } %>
            </table>
        <% } else { %>
            <p>目前沒有任何通知。 / You have no notifications yet.</p>
        <% } %>

        <br><br>
        <a href="${pageContext.request.contextPath}/views/patient/dashboard.jsp">
            <button>返回預約首頁 / Back to Booking</button>
        </a>
    </div>
</body>
</html>