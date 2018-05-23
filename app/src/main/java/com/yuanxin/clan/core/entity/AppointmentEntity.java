package com.yuanxin.clan.core.entity;

import java.io.Serializable;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/21 0021 17:26
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class AppointmentEntity implements Serializable {

    private String uname;
    private String ename;
    private String time;
    private String status;
    private String reason;
    private String bVisitorPhone;
    private int statusid;
    private int visitAppointmentId;
    private int aVisitorId;
    private int bVisitorId;
    private String aVisitorRead;
    private String bVisitorRead;

    public String getaVisitorRead() {
        return aVisitorRead;
    }

    public void setaVisitorRead(String aVisitorRead) {
        this.aVisitorRead = aVisitorRead;
    }

    public String getbVisitorRead() {
        return bVisitorRead;
    }

    public void setbVisitorRead(String bVisitorRead) {
        this.bVisitorRead = bVisitorRead;
    }

    public int getbVisitorId() {
        return bVisitorId;
    }

    public void setbVisitorId(int bVisitorId) {
        this.bVisitorId = bVisitorId;
    }

    public String getbVisitorPhone() {
        return bVisitorPhone;
    }

    public void setbVisitorPhone(String bVisitorPhone) {
        this.bVisitorPhone = bVisitorPhone;
    }

    public int getaVisitorId() {
        return aVisitorId;
    }

    public void setaVisitorId(int aVisitorId) {
        this.aVisitorId = aVisitorId;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getVisitAppointmentId() {
        return visitAppointmentId;
    }

    public void setVisitAppointmentId(int visitAppointmentId) {
        this.visitAppointmentId = visitAppointmentId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusid() {
        return statusid;
    }

    public void setStatusid(int statusid) {
        this.statusid = statusid;
    }
}
