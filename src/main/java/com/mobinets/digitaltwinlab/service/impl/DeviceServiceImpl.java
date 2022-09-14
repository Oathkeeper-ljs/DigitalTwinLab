package com.mobinets.digitaltwinlab.service.impl;

import com.mobinets.digitaltwinlab.dao.DeviceMapper;
import com.mobinets.digitaltwinlab.entity.Device;
import com.mobinets.digitaltwinlab.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public List<Device> initDeviceGroup() {
        return deviceMapper.selectAll();
    }

    @Override
    public void setDeviceStatus(int deviceID) {


    }

    @Override
    public void getDeviceStatus(int deviceID) {


    }
}
