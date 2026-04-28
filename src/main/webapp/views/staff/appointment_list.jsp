<%-- 
    Document   : appointment_list
    Created on : 2026年4月27日, 下午10:59:59
    Author     : user
--%>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/tlds/cchc.tld" prefix="cchc" %>
<style>
    .search-bar {
        background: #green;
        padding: 15px;
        border-radius: 5px;
        margin-bottom: 20px;
        display: flex;
        gap: 10px;
        align-items: center;
        flex-wrap: wrap;
    }
    .search-bar input, .search-bar select {
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }
    .btn-search {
        background: #007bff;
        color: white;
        border: none;
        padding: 8px 15px;
        border-radius: 4px;
        cursor: pointer;
    }
    .status-badge {
        padding: 4px 8px;
        border-radius: 12px;
        font-size: 0.85em;
    }
    .status-pending {
        background: #ffeeba;
        color: #856404;
    }
    .status-confirmed {
        background: #c3e6cb;
        color: #155724;
    }
    .status-cancelled {
        background: #f5c6cb;
        color: #721c24;
    }
</style>

<div class="content-area">
    <form action="GetAppServlet" method="GET" class="search-bar">
        <label>Appointment ID</label>
        <input type="text" name="appointmentId">

        <label>Date:</label>
        <input type="date" name="date">

        <label>Service:</label>
        <cchc:serviceSelect /> 

        <label>Status:</label>
        <select name="status">
            <option value="">ALL</option>
            <option value="PENDING">Pending</option>
            <option value="CONFIRMED">Confirmed</option>
            <option value="CANCELLED">Cancelled</option>
            <option value="COMPLETED">Completed</option>
            <option value="NO_SHOW">No Show</option>
        </select>

        <button type="submit" class="btn-search">Serach</button>
    </form>

    <hr>

    <table border="1" style="width:100%; border-collapse: collapse; margin-top: 10px;">
        <thead>
            <tr style="background: #e9ecef;">
                <th>Appointment ID</th>
                <th>Patient</th>
                <th>Service</th> 
                <th>Date</th>
                <th>Time</th>
                <th>Status</th>
                <th>Cancel Reason</th> 
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <cchc:appointmentTable />
        </tbody>
    </table>
</div>