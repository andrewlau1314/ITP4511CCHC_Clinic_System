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
    <title><%= clinic.getName() %> - 服務與時段</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1000px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        .service-card { border:1px solid #ddd; padding:15px; margin:10px 0; border-radius:8px; }
        button { padding:12px 20px; background:#28a745; color:white; border:none; border-radius:5px; cursor:pointer; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🩺 <%= clinic.getName() %> - 選擇服務</h2>
        <p><strong>地址：</strong><%= clinic.getAddress() %></p>

        <h3>可用服務</h3>
        <% if (services != null && !services.isEmpty()) { 
            for (ServiceBean s : services) { %>
                <div class="service-card">
                    <h4><%= s.getServiceName() %></h4>
                    <p><%= s.getDescription() != null ? s.getDescription() : "" %></p>
                    <a href="timeslots.do?clinicId=<%= clinic.getClinicId() %>&serviceId=<%= s.getServiceId() %>">
                        <button>查看可預約時段 →</button>
                    </a>
                </div>
        <%   } 
           } else { %>
            <p>此診所暫無開放服務。</p>
        <% } %>
    </div>
</body>
</html>