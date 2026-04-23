<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cchc.bean.AppointmentBean, com.cchc.bean.ClinicBean" %>

<%
    ClinicBean currentClinic = (ClinicBean) session.getAttribute("currentClinic");

    // 獲取搜尋參數 (用於 UI 保持狀態)
    String searchStatus = request.getParameter("searchStatus");
    String searchUserId = request.getParameter("searchUserId");
    String searchAppId = request.getParameter("searchAppId");
    String searchDate = request.getParameter("searchDate");
    String sortBy = request.getParameter("sortBy");
%>

<style>
    .filter-bar {
        background: #fff;
        padding: 15px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        margin-bottom: 20px;
        border: 1px solid #eee;
    }
    .filter-bar form {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        align-items: flex-end;
    }
    .form-group {
        display: flex;
        flex-direction: column;
    }
    .form-group label {
        font-size: 12px;
        font-weight: bold;
        margin-bottom: 5px;
        color: #555;
    }
    .form-group input, .form-group select {
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    .data-table {
        width: 100%;
        border-collapse: collapse;
        background: white;
    }
    .data-table th, .data-table td {
        padding: 12px;
        border-bottom: 1px solid #eee;
        text-align: left;
    }
    .data-table th {
        background: #f8f9fa;
        color: #333;
        font-weight: 600;
    }
    .data-table tr:hover {
        background: #fcfcfc;
    }

    .btn {
        padding: 6px 12px;
        border-radius: 4px;
        border: none;
        cursor: pointer;
        font-size: 13px;
    }
    .btn-delete {
        background: #dc3545;
        color: white;
    }
    .btn-search {
        background: #28a745;
        color: white;
        height: 35px;
    }

    .status-select {
        padding: 5px;
        border-radius: 4px;
        border: 1px solid #ddd;
    }
</style>

<div class="filter-bar">
    <form action="dashboard.jsp" method="get">
        <input type="hidden" name="page" value="appointmentList">

        <div class="form-group">
            <label>Appointments ID</label>
            <input type="text" name="searchAppId" value="<%= (searchAppId != null) ? searchAppId : ""%>" placeholder="搜尋 ID...">
        </div>

        <div class="form-group">
            <label>User ID</label>
            <input type="text" name="searchUserId" value="<%= (searchUserId != null) ? searchUserId : ""%>" placeholder="搜尋 User...">
        </div>

        <div class="form-group">
            <label>預約日期</label>
            <input type="date" name="searchDate" value="<%= (searchDate != null) ? searchDate : ""%>">
        </div>

        <div class="form-group">
            <label>狀態過濾</label>
            <select name="searchStatus">
                <option value="">-- 全部 --</option>
                <option value="PENDING" <%= "PENDING".equals(searchStatus) ? "selected" : ""%>>PENDING</option>
                <option value="CONFIRMED" <%= "CONFIRMED".equals(searchStatus) ? "selected" : ""%>>CONFIRMED</option>
                <option value="CANCELLED" <%= "CANCELLED".equals(searchStatus) ? "selected" : ""%>>CANCELLED</option>
                <option value="COMPLETED" <%= "COMPLETED".equals(searchStatus) ? "selected" : ""%>>COMPLETED</option>
                <option value="NO_SHOW" <%= "NO_SHOW".equals(searchStatus) ? "selected" : ""%>>NO_SHOW</option>
            </select>
        </div>

        <div class="form-group">
            <label>排序方式</label>
            <select name="sortBy">
                <option value="date_desc" <%= "date_desc".equals(sortBy) ? "selected" : ""%>>日期 (新→舊)</option>
                <option value="date_asc" <%= "date_asc".equals(sortBy) ? "selected" : ""%>>日期 (舊→新)</option>
                <option value="app_id" <%= "app_id".equals(sortBy) ? "selected" : ""%>>預約編號</option>
            </select>
        </div>

        <button type="submit" class="btn btn-search">🔍 執行篩選</button>
    </form>
</div>

<table class="data-table">
    <thead>
        <tr>
            <th>ID</th>
            <th>用戶</th>
            <th>日期</th>
            <th>時間</th>
            <th>目前狀態 (點擊更改)</th>
            <th>取消原因</th>
            <th>操作</th>
        </tr>
    </thead>
    <tbody>
        <%-- 此處應由 Servlet 調用 AppointmentDB 並將 List 放入 request 屬性 --%>
        <%-- 以下為根據 AppointmentBean 欄位模擬的循環輸出 --%>
        <tr>
            <td>101</td>
            <td>User #5</td>
            <td>2026-05-01</td>
            <td>09:00</td>
            <td>
                <select class="status-select" onchange="handleStatusChange(101, this.value)">
                    <option value="PENDING" selected>PENDING</option>
                    <option value="CONFIRMED">CONFIRMED</option>
                    <option value="CANCELLED">CANCELLED</option>
                    <option value="COMPLETED">COMPLETED</option>
                    <option value="NO_SHOW">NO_SHOW</option>
                </select>
            </td>
            <td id="reason-101">--</td>
            <td>
                <button class="btn btn-delete" onclick="handleDelete(101)">🗑️ 刪除</button>
            </td>
        </tr>
    </tbody>
</table>

<script>
    // 處理狀態變更
    function handleStatusChange(appId, newStatus) {
        let reason = "";
        if (newStatus === 'CANCELLED') {
            reason = prompt("請輸入取消原因 (Cancel Reason):");
            if (!reason) {
                alert("取消預約必須填寫原因！");
                location.reload(); // 放棄更改，刷新頁面
                return;
            }
        }

        if (confirm("確定將預約 #" + appId + " 狀態改為 " + newStatus + " 嗎？")) {
            // 導向控制器處理 Servlet (對應 AppointmentDB.updateStatus 和 updateCancelReason)
            let url = "updateAppointment.do?action=updateStatus&appId=" + appId +
                    "&status=" + newStatus + "&reason=" + encodeURIComponent(reason);
            window.location.href = url;
        }
    }

    // 處理刪除 (對應 AppointmentDB.deleteAppointment)
    function handleDelete(appId) {
        if (confirm("警告：確定要刪除預約 #" + appId + " 嗎？此操作不可逆。")) {
            window.location.href = "updateAppointment.do?action=delete&appId=" + appId;
        }
    }
</script>