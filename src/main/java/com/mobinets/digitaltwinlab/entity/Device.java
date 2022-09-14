package com.mobinets.digitaltwinlab.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class Device {

    private int deviceID;
    private int deviceType;
    private int deviceStatus;
    private Date changeTime;
    private int userId;

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
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
                "deviceID=" + deviceID +
                ", deviceType=" + deviceType +
                ", deviceStatus=" + deviceStatus +
                ", changeTime=" + changeTime +
                ", userId=" + userId +
                '}';
    }

    public String JSONString() {
        return '{' +
                "deviceID:" + deviceID +
                        ", deviceType:" + deviceType +
                        ", deviceStatus:" + deviceStatus +
                        ", changeTime:" + changeTime +
                        ", userId:" + userId +
                        '}';
    }

    public String toJSONString() {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(this);
        return jsonObject.toJSONString();
    }
}
