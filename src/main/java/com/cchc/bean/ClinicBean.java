/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.bean;

/**
 *
 * @author firetruck
 */

import java.io.Serializable;
import java.time.LocalTime;

public class ClinicBean implements Serializable {

    private int clinicId;
    private String name;
    private String address;
    private String phone;
    private String dayOff;
    private LocalTime lunchBreakStart;
    private LocalTime lunchBreakEnd;
    private LocalTime openTime;
    private LocalTime closeTime;

    public ClinicBean() {
    }

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDayOff() {
        return dayOff;
    }

    public void setDayOff(String dayOff) {
        this.dayOff = dayOff;
    }

    public LocalTime getLunchBreakStart() {
        return lunchBreakStart;
    }

    public void setLunchBreakStart(LocalTime lunchBreakStart) {
        this.lunchBreakStart = lunchBreakStart;
    }

    public LocalTime getLunchBreakEnd() {
        return lunchBreakEnd;
    }

    public void setLunchBreakEnd(LocalTime lunchBreakEnd) {
        this.lunchBreakEnd = lunchBreakEnd;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }
}
