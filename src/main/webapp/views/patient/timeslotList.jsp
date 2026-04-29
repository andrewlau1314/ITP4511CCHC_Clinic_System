<%--
    Document : timeslotList
    Created on : 2026年4月28日, 下午10:59:59
    Author   : user
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ClinicBean" %>
<%@ page import="com.cchc.bean.AppointmentBean" %>
<%@ page import="java.util.ArrayList" %>

<%
    ClinicBean clinic = (ClinicBean) request.getAttribute("clinic");
    Integer serviceId = (Integer) request.getAttribute("serviceId");
    ArrayList<AppointmentBean> timeslots = (ArrayList<AppointmentBean>) request.getAttribute("timeslots");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- Title -->
    <title>可預約時段 - <%= clinic.getName() %> | Available Time Slots - <%= clinic.getName() %></title>
    
    <style>
        body { 
            font-family: Arial; 
            background:#f4f7f6; 
            margin:0; 
            padding:20px; 
        }
        .container { 
            max-width:1000px; 
            margin:auto; 
            background:white; 
            padding:30px; 
            border-radius:10px; 
            box-shadow:0 0 15px rgba(0,0,0,0.1); 
        }
        .timeslot-card { 
            border:1px solid #ddd; 
            padding:15px; 
            margin:12px 0; 
            border-radius:8px; 
            display:flex; 
            justify-content:space-between; 
            align-items:center; 
        }
        button { 
            padding:12px 25px; 
            background:#28a745; 
            color:white; 
            border:none; 
            border-radius:5px; 
            cursor:pointer; 
            font-size:16px; 
        }
        button:hover { background:#218838; }
        .no-slot { 
            color:#dc3545; 
            font-size:18px; 
            padding:20px; 
            text-align:center; 
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Page Header -->
        <h2>
            🩺 <%= clinic.getName() %> - 可預約時段 | 
            <%= clinic.getName() %> - Available Time Slots
        </h2>
        
        <!-- Clinic Address -->
        <p>
            <strong>地址： | Address: </strong>
            <%= clinic.getAddress() %>
        </p>
        
        <h3>可用時段 / Available Time Slots</h3>
        
        <% if (timeslots != null && !timeslots.isEmpty()) {
            for (AppointmentBean ts : timeslots) { %>
                <div class="timeslot-card">
                    <div>
                        <strong><%= ts.getAppointmentDate() %></strong><br>
                        <%= ts.getAppointmentTime() %>
                    </div>
                    <form action="${pageContext.request.contextPath}/book.do" method="post" style="margin:0;">
                        <input type="hidden" name="clinicId" value="<%= clinic.getClinicId() %>">
                        <input type="hidden" name="serviceId" value="<%= serviceId %>">
                        <input type="hidden" name="appointmentDate" value="<%= ts.getAppointmentDate() %>">
                        <input type="hidden" name="appointmentTime" value="<%= ts.getAppointmentTime() %>">
                        <button type="submit">立即預約 / Book Now</button>
                    </form>
                </div>
        <% }
           } else { %>
            <!-- No Time Slots Message -->
            <p class="no-slot">
                ❌ 此服務目前沒有可預約時段 | 
                ❌ No available time slots for this service at the moment
            </p>
        <% } %>
        
        <br>
        <!-- Back Button -->
        <a href="${pageContext.request.contextPath}/patient/clinics.do">
            <button>← 返回診所列表 | ← Back to Clinic List</button>
        </a>
    </div>
</body>
</html>