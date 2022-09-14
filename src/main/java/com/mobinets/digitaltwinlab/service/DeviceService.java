package com.mobinets.digitaltwinlab.service;

import com.mobinets.digitaltwinlab.entity.Device;

import java.util.List;

public interface DeviceService {
    /**
     * 获取所有设备的信息，保存在entity中
     */
    public List<Device> initDeviceGroup();

    /**
     * 设置ID设备的信息
     * @param deviceID 设备ID
     */
    public void setDeviceStatus(int deviceID);

    /**
     * 获取ID设备的信息
     * @param deviceID 设备ID
     */
    public void getDeviceStatus(int deviceID);

}
