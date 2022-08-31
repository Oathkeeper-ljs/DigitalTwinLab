package com.mobinets.digitaltwinlab.entity;

import java.util.Date;

public class Device {

    private int id;
    private int type;
    private int status;
    private Date changeTime;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", type=" + type +
                ", status=" + status +
                ", changeTime=" + changeTime +
                ", userId=" + userId +
                '}';
    }
}
