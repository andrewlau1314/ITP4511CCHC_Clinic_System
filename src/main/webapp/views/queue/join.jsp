<%-- 
    Document   : join
    Created on : 2026年4月29日, 下午1:04:16
    Author     : user
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ClinicBean" %>
<%@ page import="com.cchc.bean.ServiceBean" %>
<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<ClinicBean> clinics = (ArrayList<ClinicBean>) request.getAttribute("clinics");
    ArrayList<ServiceBean> services = (ArrayList<ServiceBean>) request.getAttribute("services");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>加入即日排隊 / Join Same-Day Queue - CCHC</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:900px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        select, button { padding:12px; font-size:16px; margin:10px 0; width:100%; }
        button { background:#28a745; color:white; border:none; border-radius:8px; cursor:pointer; }
        button:hover { background:#218838; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🚶 加入即日排隊 / Join Same-Day Queue</h2>

        <form action="${pageContext.request.contextPath}/queue/join.do" method="post">
            <label>選擇診所 / Choose Clinic</label>
            <select name="clinicId" required>
                <% for (ClinicBean c : clinics) { %>
                    <option value="<%= c.getClinicId() %>"><%= c.getName() %></option>
                <% } %>
            </select>

            <label>選擇服務 / Choose Service</label>
            <select name="serviceId" required>
                <% for (ServiceBean s : services) { %>
                    <option value="<%= s.getServiceId() %>"><%= s.getServiceName() %></option>
                <% } %>
            </select>

            <br><br>
            <button type="submit">🚶 立即加入排隊 / Join Queue Now</button>
        </form>

        <br>
        <a href="${pageContext.request.contextPath}/views/patient/dashboard.jsp">
        <button>
        ← 返回首頁 / Back to Home</button></a>
    </div>
</body>
</html>
