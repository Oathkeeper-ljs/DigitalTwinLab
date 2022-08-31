package com.mobinets.digitaltwinlab.dao;

import com.mobinets.digitaltwinlab.entity.Device;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceMapper {

    Device selectById(int id);

    List<Device> selectByType(int type);

    int insertDevice(Device device);

    int updateStatus(int id, int status);

    int updateChangeTime(int id, Date changeTime);

    int updateUserId(int id, int userId);


}
