package com.cchc.bean;

import java.time.LocalDateTime;

public class NotificationBean implements java.io.Serializable {
    private int notifId;
    private int userId;
    private String title;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
    private String type;

    // Getters and Setters
    public int getNotifId() { return notifId; }
    public void setNotifId(int notifId) { this.notifId = notifId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}