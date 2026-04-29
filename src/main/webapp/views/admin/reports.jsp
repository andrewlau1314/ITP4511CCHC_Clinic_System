<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ArrayList<Map<String, Object>> clinicStats = (ArrayList<Map<String, Object>>) request.getAttribute("clinicStats");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系統報表 / Reports - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1300px; margin:auto; background:white; padding:30px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        .header { background:#343a40; color:white; padding:20px; border-radius:10px; text-align:center; margin-bottom:25px; }
        .card-grid { display:grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap:20px; margin:25px 0; }
        .card { background:#f8f9fa; padding:20px; border-radius:10px; border-left:6px solid #007bff; }
        table { width:100%; border-collapse:collapse; margin:25px 0; }
        th, td { padding:14px; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#343a40; color:white; }
        .number { font-size:32px; font-weight:bold; color:#28a745; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>📊 系統報表 / System Reports</h2>
            <p>管理員：<%= currentUser.getFullName() %> | <%= new java.util.Date() %></p>
        </div>

        <!-- 總覽卡片 -->
        <div class="card-grid">
            <div class="card">
                <h3>總預約數</h3>
                <div class="number">${totalBookings}</div>
            </div>
            <div class="card">
                <h3>已確認</h3>
                <div class="number">${confirmedBookings}</div>
            </div>
            <div class="card">
                <h3>待確認</h3>
                <div class="number">${pendingBookings}</div>
            </div>
            <div class="card">
                <h3>已取消 / No-Show</h3>
                <div class="number">${cancelledBookings + noShowBookings}</div>
            </div>
        </div>

        <!-- 各診所統計 -->
        <h3>📍 各診所預約統計</h3>
        <table>
            <tr>
                <th>診所名稱</th>
                <th>總預約</th>
                <th>已確認</th>
                <th>確認率</th>
            </tr>
            <% if (clinicStats != null) { 
                for (Map<String, Object> c : clinicStats) { 
                    int total = c.get("total") != null ? ((Number) c.get("total")).intValue() : 0;
                    int confirmed = c.get("confirmed") != null ? ((Number) c.get("confirmed")).intValue() : 0;
                    int rate = total > 0 ? (confirmed * 100 / total) : 0;
            %>
            <tr>
                <td><strong><%= c.get("clinicName") %></strong></td>
                <td><%= total %></td>
                <td><%= confirmed %></td>
                <td><%= rate %>%</td>
            </tr>
            <% } } %>
        </table>

        <br>
        <a href="${pageContext.request.contextPath}/admin/dashboard.do">
            <button>← 返回管理員首頁 / Back to Dashboard</button>
        </a>
    </div>
</body>
</html>