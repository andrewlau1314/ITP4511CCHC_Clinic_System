<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.UserBean" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新增用戶 / Add New User - CCHC Admin</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:700px; margin:auto; background:white; padding:40px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        input, select { width:100%; padding:12px; margin:8px 0; border:1px solid #ccc; border-radius:5px; box-sizing:border-box; }
        button { padding:12px 30px; background:#007bff; color:white; border:none; border-radius:5px; cursor:pointer; font-size:16px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>👤 新增用戶 / Add New User</h2>

        <form method="post" action="${pageContext.request.contextPath}/admin/addUser.do">
            <label>帳號 / Username</label>
            <input type="text" name="username" required>

            <label>密碼 / Password</label>
            <input type="password" name="password" required>

            <label>姓名 / Full Name</label>
            <input type="text" name="fullName" required>

            <label>Email</label>
            <input type="email" name="email">

            <label>電話 / Phone</label>
            <input type="text" name="phone">

            <label>角色 / Role</label>
            <select name="role" required>
                <option value="PATIENT">病人 / Patient</option>
                <option value="STAFF">職員 / Staff</option>
                <option value="ADMIN">管理員 / Admin</option>
            </select>

            <label>所屬診所ID (職員專用) / Clinic ID (Staff only)</label>
            <input type="number" name="clinicId" placeholder="只有職員需要填寫，其餘留空">

            <br><br>
            <button type="submit">✅ 確認新增 / Add User</button>
        </form>

        <br>
        <a href="${pageContext.request.contextPath}/admin/manageUsers.do">
            <button>← 返回用戶列表 / Back to User List</button>
        </a>
    </div>
</body>
</html>