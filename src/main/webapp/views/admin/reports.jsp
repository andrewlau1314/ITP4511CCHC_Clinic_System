<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="com.cchc.bean.AppointmentBean" %>
<%@ page import="com.cchc.bean.ClinicBean" %>
<%@ page import="com.cchc.bean.ServiceBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ArrayList<AppointmentBean> appointments = (ArrayList<AppointmentBean>) request.getAttribute("appointments");
    ArrayList<Map<String, Object>> clinicStats = (ArrayList<Map<String, Object>>) request.getAttribute("clinicStats");
    ArrayList<ClinicBean> clinics = (ArrayList<ClinicBean>) request.getAttribute("clinics");
    ArrayList<ServiceBean> services = (ArrayList<ServiceBean>) request.getAttribute("services");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系統報表 / Advanced Reports - CCHC Admin</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1400px; margin:auto; background:white; padding:30px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        .filter-bar { background:#f8f9fa; padding:20px; border-radius:10px; margin-bottom:25px; }
        table { width:100%; border-collapse:collapse; margin:25px 0; }
        th, td { padding:12px; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#343a40; color:white; }
        .number { font-size:32px; font-weight:bold; color:#28a745; }
        .chart-container { margin:30px 0; padding:20px; background:#f8f9fa; border-radius:10px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>📊 系統報表 / Advanced Reports</h2>
        <p>管理員：<%= currentUser.getFullName() %></p>

        <!-- 篩選表單 -->
        <div class="filter-bar">
            <form method="get" action="${pageContext.request.contextPath}/admin/reports.do">
                <label>診所：</label>
                <select name="clinicId">
                    <option value="">全部診所</option>
                    <% if (clinics != null) for (ClinicBean c : clinics) { %>
                        <option value="<%= c.getClinicId() %>"><%= c.getName() %></option>
                    <% } %>
                </select>

                <label>服務：</label>
                <select name="serviceId">
                    <option value="">全部服務</option>
                    <% if (services != null) for (ServiceBean s : services) { %>
                        <option value="<%= s.getServiceId() %>"><%= s.getServiceName() %></option>
                    <% } %>
                </select>

                <label>月份：</label>
                <input type="month" name="monthYear">

                <label>狀態：</label>
                <select name="status">
                    <option value="">全部</option>
                    <option value="PENDING">待確認</option>
                    <option value="CONFIRMED">已確認</option>
                    <option value="CANCELLED">已取消</option>
                    <option value="NO_SHOW">未到診</option>
                </select>

                <button type="submit">🔍 查詢</button>
            </form>
        </div>

        <!-- 總覽統計 -->
        <div style="display:flex; gap:20px; margin:20px 0;">
            <div style="background:#f8f9fa; padding:20px; border-radius:8px; flex:1;">
                <strong>總預約數</strong><br>
                <span class="number">${totalBookings}</span>
            </div>
            <div style="background:#f8f9fa; padding:20px; border-radius:8px; flex:1;">
                <strong>已確認</strong><br>
                <span class="number">${confirmedBookings}</span>
            </div>
            <div style="background:#f8f9fa; padding:20px; border-radius:8px; flex:1;">
                <strong>待確認</strong><br>
                <span class="number">${pendingBookings}</span>
            </div>
        </div>

        <!-- 圖表 -->
        <div class="chart-container">
            <h3>📈 各診所預約統計</h3>
            <canvas id="clinicChart" height="100"></canvas>
        </div>

        <!-- 預約列表 -->
        <h3>預約記錄</h3>
        <table>
            <tr>
                <th>日期</th>
                <th>時間</th>
                <th>診所名稱</th>
                <th>服務名稱</th>
                <th>病人姓名</th>
                <th>狀態</th>
            </tr>
            <% if (appointments != null && !appointments.isEmpty()) { 
                for (AppointmentBean ab : appointments) { %>
            <tr>
                <td><%= ab.getAppointmentDate() %></td>
                <td><%= ab.getAppointmentTime() %></td>
                <td><%= ab.getClinicName() != null ? ab.getClinicName() : "-" %></td>
                <td><%= ab.getServiceName() != null ? ab.getServiceName() : ab.getServiceId() %></td>
                <td><strong><%= ab.getPatientName() != null ? ab.getPatientName() : ab.getUserId() %></strong></td>
                <td><%= ab.getStatus() %></td>
            </tr>
            <% } } else { %>
            <tr><td colspan="6" style="text-align:center; padding:40px;">沒有符合條件的記錄</td></tr>
            <% } %>
        </table>

        <br>
        <a href="${pageContext.request.contextPath}/admin/dashboard.do">
            <button>← 返回管理員首頁</button>
        </a>
    </div>

    <script>
        // 各診所長條圖
        new Chart(document.getElementById('clinicChart'), {
            type: 'bar',
            data: {
                labels: [<% for (Map m : clinicStats) { %>"<%= m.get("clinicName") %>", <% } %>],
                datasets: [{
                    label: '總預約數',
                    data: [<% for (Map m : clinicStats) { %><%= m.get("total") %>, <% } %>],
                    backgroundColor: '#007bff'
                }]
            },
            options: { responsive: true, scales: { y: { beginAtZero: true } } }
        });
    </script>
</body>
</html>