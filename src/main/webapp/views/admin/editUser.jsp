<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.UserBean" %>
<%
    UserBean userToEdit = (UserBean) request.getAttribute("userToEdit");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>編輯用戶 / Edit User - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:700px; margin:auto; background:white; padding:40px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        input, select { width:100%; padding:12px; margin:10px 0; border:1px solid #ccc; border-radius:6px; box-sizing:border-box; }
        button { padding:14px 30px; background:#ffc107; color:black; border:none; border-radius:8px; font-size:16px; cursor:pointer; }
        .back { background:#6c757d; color:white; }
    </style>
</head>
<body>
    <div class="container">
        <h2>✏️ 編輯用戶 / Edit User</h2>

        <form method="post" action="${pageContext.request.contextPath}/admin/editUser.do">
            <input type="hidden" name="userId" value="<%= userToEdit.getUserId() %>">

            <label>帳號 / Username (不可修改)</label>
            <input type="text" value="<%= userToEdit.getUsername() %>" disabled>

            <label>姓名 / Full Name</label>
            <input type="text" name="fullName" value="<%= userToEdit.getFullName() %>" required>

            <label>Email</label>
            <input type="email" name="email" value="<%= userToEdit.getEmail() != null ? userToEdit.getEmail() : "" %>">

            <label>電話 / Phone</label>
            <input type="text" name="phone" value="<%= userToEdit.getPhone() != null ? userToEdit.getPhone() : "" %>">

            <label>角色 / Role</label>
            <select name="role" required>
                <option value="PATIENT" <%= "PATIENT".equals(userToEdit.getRole()) ? "selected" : "" %>>病人 / Patient</option>
                <option value="STAFF" <%= "STAFF".equals(userToEdit.getRole()) ? "selected" : "" %>>職員 / Staff</option>
                <option value="ADMIN" <%= "ADMIN".equals(userToEdit.getRole()) ? "selected" : "" %>>管理員 / Admin</option>
            </select>

            <label>所屬診所ID（僅職員需要） / Clinic ID</label>
            <input type="number" name="clinicId" value="<%= userToEdit.getClinicId() != 0 ? userToEdit.getClinicId() : "" %>" placeholder="PATIENT 或 ADMIN 請留空">

            <label>帳號狀態 / Status</label>
            <select name="active">
                <option value="1" <%= userToEdit.isActive() ? "selected" : "" %>>✅ 啟用</option>
                <option value="0" <%= !userToEdit.isActive() ? "selected" : "" %>>❌ 停用</option>
            </select>

            <br><br>
            <button type="submit">💾 儲存修改 / Save Changes</button>
        </form>

        <br>
        <a href="${pageContext.request.contextPath}/admin/manageUsers.do">
            <button class="back">← 返回用戶列表 / Back to User List</button>
        </a>
    </div>
</body>
</html>