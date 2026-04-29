<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新增診所 / Add New Clinic - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:800px; margin:auto; background:white; padding:40px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        input, select, textarea { width:100%; padding:12px; margin:10px 0; border:1px solid #ccc; border-radius:6px; box-sizing:border-box; }
        button { padding:14px 30px; background:#28a745; color:white; border:none; border-radius:8px; font-size:16px; cursor:pointer; }
        .back { background:#6c757d; }
        .checkbox-group { margin:15px 0; }
        .checkbox-group label { margin-right:15px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🏥 新增診所 / Add New Clinic</h2>

        <form method="post" action="${pageContext.request.contextPath}/admin/addClinic.do">
            <label>診所名稱 / Clinic Name *</label>
            <input type="text" name="name" required>

            <label>地址 / Address</label>
            <input type="text" name="address">

            <label>電話 / Phone</label>
            <input type="text" name="phone">

            <!-- ==================== 多選 Checkbox ==================== -->
            <label>休息日 / Day Off（可多選）</label>
            <div class="checkbox-group">
                <input type="checkbox" name="dayOff" value="Mon"> Mon
                <input type="checkbox" name="dayOff" value="Tue"> Tue
                <input type="checkbox" name="dayOff" value="Wed"> Wed
                <input type="checkbox" name="dayOff" value="Thu"> Thu
                <input type="checkbox" name="dayOff" value="Fri"> Fri
                <input type="checkbox" name="dayOff" value="Sat"> Sat
                <input type="checkbox" name="dayOff" value="Sun"> Sun
            </div>

            <label>午休開始時間 / Lunch Break Start (HH:mm)</label>
            <input type="time" name="lunchBreakStart" required>

            <label>午休結束時間 / Lunch Break End (HH:mm)</label>
            <input type="time" name="lunchBreakEnd" required>

            <label>開診時間 / Open Time (HH:mm)</label>
            <input type="time" name="openTime" required>

            <label>關診時間 / Close Time (HH:mm)</label>
            <input type="time" name="closeTime" required>

            <br><br>
            <button type="submit">✅ 確認新增診所 / Add Clinic</button>
        </form>

        <br>
        <a href="${pageContext.request.contextPath}/admin/manageClinics.do">
            <button class="back">← 返回診所列表 / Back to Clinics List</button>
        </a>
    </div>
</body>
</html>