<%-- 
    Document   : login
    Created on : 2026年4月19日, 下午6:26:48
    Author     : user
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CCHC 社區診所 - 登入</title>
    <style>
        body { font-family: Arial; background: #f4f7f6; display:flex; justify-content:center; align-items:center; height:100vh; margin:0; }
        .login-box { background:white; padding:40px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); width:380px; }
        input { width:100%; padding:12px; margin:10px 0; border:1px solid #ccc; border-radius:5px; }
        button { width:100%; padding:12px; background:#007bff; color:white; border:none; border-radius:5px; font-size:16px; cursor:pointer; }
        button:hover { background:#0056b3; }
        .error { color:red; text-align:center; margin:10px 0; }
    </style>
</head>
<body>
    <div class="login-box">
        <h2 style="text-align:center; color:#007bff;">🩺 CCHC 社區診所系統</h2>
        <h2 style="text-align:center; color:#007bff;">🩺 CCHC clinics System</h2>
        <h3 style="text-align:center;">Login</h3>
        
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        
        <form action="login.do" method="post">
            <input type="text" name="username" placeholder="用戶名稱" required autofocus />
            <input type="password" name="password" placeholder="密碼" required />
            <button type="submit">Login</button>
        </form>
        
        <p style="text-align:center; margin-top:15px; font-size:14px;">
            測試帳號：<br>
            admin/ 123456 (管理員)<br>
            staff1/ 123456 (職員)<br>
            patient1/ 123456 (病人)
        </p>
    </div>
</body>
</html>