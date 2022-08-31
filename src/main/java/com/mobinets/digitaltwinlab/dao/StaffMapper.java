package com.mobinets.digitaltwinlab.dao;

import com.mobinets.digitaltwinlab.entity.Staff;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StaffMapper {

    Staff selectByCampusNum(long campusNum);

    Staff selectByName(String name);

    int insertStaff(Staff staff);

    int updateType(long campusNum, int type);

}
