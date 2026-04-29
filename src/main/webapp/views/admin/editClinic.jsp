<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ClinicBean" %>
<%
    ClinicBean clinic = (ClinicBean) request.getAttribute("clinic");
    String[] selectedDays = (clinic.getDayOff() != null && !clinic.getDayOff().isEmpty()) 
                          ? clinic.getDayOff().split(",") : new String[0];
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>編輯診所 / Edit Clinic - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:800px; margin:auto; background:white; padding:40px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        input, select, textarea { width:100%; padding:12px; margin:10px 0; border:1px solid #ccc; border-radius:6px; box-sizing:border-box; }
        button { padding:14px 30px; background:#ffc107; color:black; border:none; border-radius:8px; font-size:16px; cursor:pointer; }
        .back { background:#6c757d; color:white; }
        .checkbox-group label { margin-right:15px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>✏️ 編輯診所 / Edit Clinic</h2>

        <form method="post" action="${pageContext.request.contextPath}/admin/editClinic.do">
            <input type="hidden" name="clinicId" value="<%= clinic.getClinicId() %>">

            <label>診所名稱 / Clinic Name *</label>
            <input type="text" name="name" value="<%= clinic.getName() %>" required>

            <label>地址 / Address</label>
            <input type="text" name="address" value="<%= clinic.getAddress() != null ? clinic.getAddress() : "" %>">

            <label>電話 / Phone</label>
            <input type="text" name="phone" value="<%= clinic.getPhone() != null ? clinic.getPhone() : "" %>">

            <label>休息日 / Day Off（可多選）</label>
            <div class="checkbox-group">
                <input type="checkbox" name="dayOff" value="Mon" <%= java.util.Arrays.asList(selectedDays).contains("Mon") ? "checked" : "" %>> Mon
                <input type="checkbox" name="dayOff" value="Tue" <%= java.util.Arrays.asList(selectedDays).contains("Tue") ? "checked" : "" %>> Tue
                <input type="checkbox" name="dayOff" value="Wed" <%= java.util.Arrays.asList(selectedDays).contains("Wed") ? "checked" : "" %>> Wed
                <input type="checkbox" name="dayOff" value="Thu" <%= java.util.Arrays.asList(selectedDays).contains("Thu") ? "checked" : "" %>> Thu
                <input type="checkbox" name="dayOff" value="Fri" <%= java.util.Arrays.asList(selectedDays).contains("Fri") ? "checked" : "" %>> Fri
                <input type="checkbox" name="dayOff" value="Sat" <%= java.util.Arrays.asList(selectedDays).contains("Sat") ? "checked" : "" %>> Sat
                <input type="checkbox" name="dayOff" value="Sun" <%= java.util.Arrays.asList(selectedDays).contains("Sun") ? "checked" : "" %>> Sun
            </div>

            <label>午休開始時間 / Lunch Break Start</label>
            <input type="time" name="lunchBreakStart" value="<%= clinic.getLunchBreakStart() %>" required>

            <label>午休結束時間 / Lunch Break End</label>
            <input type="time" name="lunchBreakEnd" value="<%= clinic.getLunchBreakEnd() %>" required>

            <label>開診時間 / Open Time</label>
            <input type="time" name="openTime" value="<%= clinic.getOpenTime() %>" required>

            <label>關診時間 / Close Time</label>
            <input type="time" name="closeTime" value="<%= clinic.getCloseTime() %>" required>

            <br><br>
            <button type="submit">💾 儲存修改 / Save Changes</button>
        </form>

        <br>
        <a href="${pageContext.request.contextPath}/admin/manageClinics.do">
            <button class="back">← 返回診所列表 / Back to Clinics List</button>
        </a>
    </div>
</body>
</html>