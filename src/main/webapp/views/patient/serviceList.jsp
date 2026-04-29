<%-- 
    Document   : serviceList
    Created on : 2026年4月26日, 下午3:27:23
    Author     : Andrew
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ClinicBean" %>
<%@ page import="com.cchc.bean.ServiceBean" %>
<%@ page import="java.util.ArrayList" %>
<%
    ClinicBean clinic = (ClinicBean) request.getAttribute("clinic");
    ArrayList<ServiceBean> services = (ArrayList<ServiceBean>) request.getAttribute("services");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>選擇服務 - <%= clinic.getName() %></title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1000px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        .service-card { border:1px solid #ddd; padding:20px; margin:15px 0; border-radius:10px; }
        button { padding:12px 30px; background:#007bff; color:white; border:none; border-radius:8px; cursor:pointer; font-size:16px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🩺 <%= clinic.getName() %> - 選擇服務</h2>
        <p><strong>地址：</strong><%= clinic.getAddress() %></p>

        <% for (ServiceBean s : services) { %>
            <div class="service-card">
                <h3><%= s.getServiceName() %></h3>
                <p><%= s.getDescription() %></p>
                <form action="${pageContext.request.contextPath}/dateSelection.do" method="get" style="margin:0;">
                    <input type="hidden" name="clinicId" value="<%= clinic.getClinicId() %>">
                    <input type="hidden" name="serviceId" value="<%= s.getServiceId() %>">
                    <button type="submit">選擇此服務 → 選擇日期</button>
                </form>
            </div>
        <% } %>

        <br>
        <a href="${pageContext.request.contextPath}/patient/clinics.do">
            <button>← 返回診所列表</button>    
        </a>
    </div>
</body>
</html>