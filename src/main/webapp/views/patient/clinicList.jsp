<%-- 
    Document   : clinicList
    Created on : 2026年4月23日, 下午7:30:11
    Author     : user
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ClinicBean" %>
<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<ClinicBean> clinics = (ArrayList<ClinicBean>) request.getAttribute("clinics");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>選擇診所 - CCHC 社區診所系統</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1100px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        .clinic-card { border:1px solid #ddd; padding:20px; margin:15px 0; border-radius:8px; }
        .clinic-card h3 { margin:0 0 12px 0; color:#007bff; }
        button { padding:12px 25px; background:#007bff; color:white; border:none; border-radius:5px; cursor:pointer; font-size:16px; }
        button:hover { background:#0056b3; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🩺 選擇診所預約</h2>
        <p>請選擇您想預約的社區診所</p>

        <% if (clinics != null && !clinics.isEmpty()) { 
            for (ClinicBean c : clinics) { %>
                <div class="clinic-card">
                    <h3><%= c.getName() %></h3>
                    <p><strong>地址：</strong><%= c.getAddress() != null ? c.getAddress() : "" %></p>
                    <p><strong>電話：</strong><%= c.getPhone() != null ? c.getPhone() : "" %></p>
                    <a href="${pageContext.request.contextPath}/services.do?clinicId=<%= c.getClinicId() %>">
                        <button>查看服務與可預約時段 →</button>
                    </a>
                </div>
        <%   } 
           } else { %>
            <p style="color:red;">目前沒有診所資料，請聯絡管理員。</p>
        <% } %>
    </div>
</body>
</html>