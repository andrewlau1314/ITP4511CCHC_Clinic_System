<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ServiceBean" %>
<%
    ServiceBean service = (ServiceBean) request.getAttribute("service");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>編輯服務 / Edit Service - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:700px; margin:auto; background:white; padding:40px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        input, textarea { width:100%; padding:12px; margin:10px 0; border:1px solid #ccc; border-radius:6px; box-sizing:border-box; }
        button { padding:14px 30px; background:#ffc107; color:black; border:none; border-radius:8px; font-size:16px; cursor:pointer; }
        .back { background:#6c757d; color:white; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🩺 編輯服務 / Edit Service</h2>

        <form method="post" action="${pageContext.request.contextPath}/admin/editService.do">
            <input type="hidden" name="serviceId" value="<%= service.getServiceId() %>">

            <label>服務名稱 / Service Name *</label>
            <input type="text" name="serviceName" value="<%= service.getServiceName() %>" required>

            <label>服務描述 / Description</label>
            <textarea name="description" rows="6"><%= service.getDescription() != null ? service.getDescription() : "" %></textarea>

            <br><br>
            <button type="submit">💾 儲存修改 / Save Changes</button>
        </form>

        <br>
        <a href="${pageContext.request.contextPath}/admin/manageServices.do">
            <button class="back">← 返回服務列表 / Back to Services List</button>
        </a>
    </div>
</body>
</html>