<%-- 
    Document   : edit_appointment
    Created on : 2026年4月29日, 上午3:12:50
    Author     : firetruck
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Edit Appointment - CCHC</title>
        <style>
            .edit-container {
                max-width: 500px;
                margin: 20px auto;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 8px;
                font-family: Arial;
            }
            .form-group {
                margin-bottom: 15px;
            }
            .form-group label {
                display: block;
                font-weight: bold;
                margin-bottom: 5px;
            }
            .form-group input, .form-group select, .form-group textarea {
                width: 100%;
                padding: 8px;
                box-sizing: border-box;
            }
            .readonly-box {
                background: #e9ecef;
                cursor: not-allowed;
            }
            .quota-display {
                margin-top: 5px;
                padding: 8px;
                background-color: #e3f2fd;
                border-radius: 4px;
                color: #0d47a1;
                font-weight: bold;
            }
            .btn-submit {
                background: #28a745;
                color: white;
                border: none;
                padding: 10px 20px;
                cursor: pointer;
                border-radius: 4px;
            }
            .btn-cancel {
                background: #6c757d;
                color: white;
                text-decoration: none;
                padding: 10px 20px;
                border-radius: 4px;
                display: inline-block;
            }
        </style>
    </head>
    <body>

        <div class="edit-container">
            <h2>Edit Appointment ID: ${app.appointmentId}</h2>
            <c:if test="${not empty param.error}">
                <div class="error-msg" style="color: white; background-color: #dc3545; padding: 10px; border-radius: 4px; margin-bottom: 15px;">
                    <c:choose>
                        <c:when test="${param.error == 'full'}">
                            The service quota for this day is full!
                        </c:when>
                        <c:when test="${param.error == 'time_taken'}">
                            This time slot (<strong>${app.appointmentTime}</strong>) has already been taken by another confirmed appointment!
                        </c:when>
                        <c:otherwise>
                            An unknown error occurred.
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
            <form action="UpdateAppointmentServlet" method="POST">
                <input type="hidden" name="appointmentId" value="${app.appointmentId}">
                <input type="hidden" name="serviceId" value="${app.serviceId}">
                <input type="hidden" name="appointmentDate" value="${app.appointmentDate}">
                <input type="hidden" name="appointmentTime" value="${app.appointmentTime}">

                <div class="form-group">
                    <label>Patient Name</label>
                    <input type="text" value="${app.fullName}" class="readonly-box" readonly>
                </div>

                <div class="form-group">
                    <label>Service </label>
                    <input type="text" class="readonly-box" value="${app.serviceName}" readonly>

                </div>

                <div class="form-group">
                    <label>Date</label>
                    <input type="date" value="${app.appointmentDate}" class="readonly-box" readonly>
                </div>

                <div class="form-group">
                    <label>Quota Status (on ${app.appointmentDate})</label>
                    <div class="quota-display">
                        <c:choose>
                            <c:when test="${remainingQuota <= 0}">
                                <span style="color: red;">No Quota Left (Remaining: 0)</span>
                            </c:when>
                            <c:otherwise>
                                Remaining Quota: ${remainingQuota}
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="form-group">
                    <label>Time</label>
                    <input type="time" value="${app.appointmentTime}" class="readonly-box" readonly>
                </div>

                <div class="form-group">
                    <label>Status</label>
                    <select name="status">
                        <option value="PENDING" ${app.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                        <option value="CONFIRMED" ${app.status == 'CONFIRMED' ? 'selected' : ''}>Confirmed</option>
                        <option value="COMPLETED" ${app.status == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                        <option value="CANCELLED" ${app.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                        <option value="NO_SHOW" ${app.status == 'NO_SHOW' ? 'selected' : ''}>No Show</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Cancel Reason</label>
                    <textarea name="cancelReason" rows="3">${app.cancelReason}</textarea>
                </div>

                <div style="margin-top: 20px;">
                    <button type="submit" class="btn-submit">Update</button>
                    <a href="GetAppServlet" class="btn-cancel">Back</a>
                </div>
            </form>
        </div>

    </body>
</html>