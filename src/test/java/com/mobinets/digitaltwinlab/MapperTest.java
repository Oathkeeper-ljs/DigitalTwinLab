package com.mobinets.digitaltwinlab;

import com.mobinets.digitaltwinlab.dao.DeviceMapper;
import com.mobinets.digitaltwinlab.dao.RoomMapper;
import com.mobinets.digitaltwinlab.dao.StaffMapper;
import com.mobinets.digitaltwinlab.dao.UserMapper;
import com.mobinets.digitaltwinlab.entity.Device;
import com.mobinets.digitaltwinlab.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = DigitalTwinLabApplication.class)
@MapperScan({"com.mobinets.digitaltwinlab.dao"})
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Test
    public void testSelectUser() {
        Device device = deviceMapper.selectById(1);
        System.out.println(device);
        System.out.println(deviceMapper.selectAll());

        List<Device> res = new ArrayList<>();
        res = deviceMapper.selectByType(1);
        for(Device device1: res) {
            System.out.println(device1);
        }

    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setCampusNum(202121080731L);
        user.setUsername("Oathkeeper");
        user.setPassword("123456");
        user.setEmail("jiangshu@mobinets.org");
        user.setSalt("abc");
        user.setType(1);
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getActivationCode());

    }

    @Test
    public void testUpdateUser() {


        deviceMapper.updateUserId(9, 102);

    }

}
