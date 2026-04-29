<%--
    Document : queueAllStatus
    Created on : 2026年4月29日
    Author   : user
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.QueueBean" %>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="java.util.ArrayList" %>

<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ArrayList<QueueBean> allQueueStatus = (ArrayList<QueueBean>) request.getAttribute("allQueueStatus");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- Title -->
    <title>各診所排隊狀態 / All Clinics Queue Status - CCHC</title>
    
    <style>
        body { 
            font-family: Arial; 
            background:#f4f7f6; 
            margin:0; 
            padding:20px; 
        }
        .container { 
            max-width:1100px; 
            margin:auto; 
            background:white; 
            padding:30px; 
            border-radius:10px; 
            box-shadow:0 0 15px rgba(0,0,0,0.1); 
        }
        table { 
            width:100%; 
            border-collapse:collapse; 
            margin-top:20px; 
        }
        th, td { 
            padding:15px; 
            text-align:left; 
            border-bottom:1px solid #ddd; 
        }
        th { 
            background:#007bff; 
            color:white; 
        }
        .number { 
            font-size:24px; 
            font-weight:bold; 
            color:#28a745; 
        }
        .my-number { 
            background:#ffc107; 
            color:black; 
            font-weight:bold; 
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Page Header -->
        <h2>👀 各診所即日排隊狀態 / All Clinics Same-Day Queue Status</h2>
        
        <!-- Current Time -->
        <p>
            目前時間： | Current Time: 
            <%= new java.util.Date() %>
        </p>

        <table>
            <tr>
                <th>診所 / Clinic</th>
                <th>目前等待人數 / Waiting People</th>
                <th>當前叫號 / Current Number</th>
                <th>您的號碼 / Your Number</th>
            </tr>
            <% for (QueueBean q : allQueueStatus) { %>
            <tr>
                <td><%= q.getClinicName() %></td>
                <td>
                    <%= q.getEstimatedWaitMin() %> 人 | 
                    <%= q.getEstimatedWaitMin() %> people
                </td>
                <td class="number">
                    <%= q.getQueueNumber() != null && q.getQueueNumber().startsWith("Q") 
                        ? q.getQueueNumber() 
                        : "目前無排隊 / No Queue" %>
                </td>
                <td>
                    <%
                        String myNum = q.getQueueNumber();
                        if (myNum != null && myNum.startsWith("Q")) {
                    %>
                        <span class="my-number"><%= myNum %></span>
                    <% } else { %>
                        -
                    <% } %>
                </td>
            </tr>
            <% } %>
        </table>

        <br><br>
        <!-- Back Button -->
        <a href="${pageContext.request.contextPath}/patient/clinics.do">
            <button>返回預約首頁 / Back to Booking Home</button>
        </a>
    </div>
</body>
</html>