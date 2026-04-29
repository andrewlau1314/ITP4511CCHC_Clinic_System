/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.bean;

import java.time.LocalDateTime;

public class QueueBean implements java.io.Serializable {
    private int queueId;
    private int userId;
    private int clinicId;
    private int serviceId;
    private LocalDateTime joinedAt;
    private String queueNumber;
    private String status;
    private int estimatedWaitMin;
    private String clinicName;   // 額外顯示用
    private String serviceName;  // 額外顯示用

    // Getters and Setters
    public int getQueueId() { return queueId; }
    public void setQueueId(int queueId) { this.queueId = queueId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getClinicId() { return clinicId; }
    public void setClinicId(int clinicId) { this.clinicId = clinicId; }
    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public LocalDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDateTime joinedAt) { this.joinedAt = joinedAt; }
    public String getQueueNumber() { return queueNumber; }
    public void setQueueNumber(String queueNumber) { this.queueNumber = queueNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getEstimatedWaitMin() { return estimatedWaitMin; }
    public void setEstimatedWaitMin(int estimatedWaitMin) { this.estimatedWaitMin = estimatedWaitMin; }
    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
}