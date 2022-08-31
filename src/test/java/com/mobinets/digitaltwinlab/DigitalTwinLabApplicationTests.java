package com.mobinets.digitaltwinlab;

import com.mobinets.digitaltwinlab.dao.StaffMapper;
import com.mobinets.digitaltwinlab.dao.UserMapper;
import com.mobinets.digitaltwinlab.entity.Staff;
import com.mobinets.digitaltwinlab.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = DigitalTwinLabApplication.class)
class DigitalTwinLabApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Test
    public void testSelectStaff() {
        Staff staff = staffMapper.selectByCampusNum(202121080731L);
        System.out.println(staff);

        staff = staffMapper.selectByName("刘江澍");
        System.out.println(staff);


    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setCampusNum(202121080730L);
        user.setPassword("zhuoliu");
        user.setEmail("zhuoliu@mobinets.org");
        user.setSalt("abc");
        user.setType(1);
        user.setStatus(1);
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());

    }

}
