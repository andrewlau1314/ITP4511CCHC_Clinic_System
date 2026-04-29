<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新增服務 / Add New Service - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:700px; margin:auto; background:white; padding:40px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        input, textarea { width:100%; padding:12px; margin:10px 0; border:1px solid #ccc; border-radius:6px; box-sizing:border-box; }
        button { padding:14px 30px; background:#28a745; color:white; border:none; border-radius:8px; font-size:16px; cursor:pointer; }
        .back { background:#6c757d; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🩺 新增服務項目 / Add New Service</h2>

        <form method="post" action="${pageContext.request.contextPath}/admin/addService.do">
            <label>服務名稱 / Service Name *</label>
            <input type="text" name="serviceName" required>

            <label>服務描述 / Description</label>
            <textarea name="description" rows="5"></textarea>

            <br><br>
            <button type="submit">✅ 確認新增 / Add Service</button>
        </form>

        <br>
        <a href="${pageContext.request.contextPath}/admin/manageServices.do">
            <button class="back">← 返回服務列表 / Back to Services List</button>
        </a>
    </div>
</body>
</html>