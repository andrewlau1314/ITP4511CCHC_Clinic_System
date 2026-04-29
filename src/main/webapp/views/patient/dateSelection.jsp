<%--
    Document : dateSelection
    Created on : 2026年4月29日, 上午1:12:41
    Author   : user
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ClinicBean" %>

<%
    ClinicBean clinic = (ClinicBean) request.getAttribute("clinic");
    Integer serviceId = (Integer) request.getAttribute("serviceId");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- Title -->
    <title>選擇日期 - <%= clinic.getName() %> | Select Date - <%= clinic.getName() %></title>
    
    <style>
        body { 
            font-family: Arial; 
            background:#f4f7f6; 
            margin:0; 
            padding:20px; 
        }
        .container { 
            max-width:800px; 
            margin:auto; 
            background:white; 
            padding:30px; 
            border-radius:10px; 
            box-shadow:0 0 15px rgba(0,0,0,0.1); 
        }
        input[type="date"] { 
            padding:12px; 
            font-size:18px; 
            width:100%; 
            margin:20px 0; 
        }
        button { 
            padding:15px 30px; 
            background:#007bff; 
            color:white; 
            border:none; 
            border-radius:8px; 
            font-size:18px; 
            cursor:pointer; 
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Page Header -->
        <h2>
            🩺 <%= clinic.getName() %> - 選擇預約日期 | 
            <%= clinic.getName() %> - Select Appointment Date
        </h2>
        
        <form action="${pageContext.request.contextPath}/timeslots.do" method="get">
            <input type="hidden" name="clinicId" value="<%= clinic.getClinicId() %>">
            <!-- 修正：確保 serviceId 不會傳 "null" 字串 -->
            <!-- Fixed: Ensure serviceId does not pass as "null" string -->
            <input type="hidden" name="serviceId" value="<%= (serviceId != null ? serviceId : "") %>">
           
            <!-- Date Selection -->
            <label><strong>請選擇日期： | Please select a date: </strong></label><br>
            <input type="date" name="appointmentDate" required min="2026-05-01" max="2026-06-30">
           
            <br><br>
            <button type="submit">
                查看該日期可用時間 → | View Available Time Slots for This Date →
            </button>
        </form>
        
        <br>
        <!-- Back Button -->
        <a href="${pageContext.request.contextPath}/patient/clinics.do">
            <button style="background-color:red">
                ← 返回診所列表 | ← Go back to the Clinic List
            </button>
        </a>
    </div>
</body>
</html>