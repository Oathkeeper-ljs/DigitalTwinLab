package com.mobinets.digitaltwinlab.dao;

import com.mobinets.digitaltwinlab.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface DeviceMapper {

    Device selectById(int id);

    List<Device> selectByType(int type);

    List<Device> selectAll();

    int insertDevice(Device device);

    int updateStatus(int id, int status);

    void updateChangeTime(int id, Date changeTime);

    void updateUserId(int id, int userId);


}
