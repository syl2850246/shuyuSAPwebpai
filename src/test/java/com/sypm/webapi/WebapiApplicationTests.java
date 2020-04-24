package com.sypm.webapi;

import com.sypm.webapi.bean.StudentBean;
import com.sypm.webapi.service.StudentService;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest
//@RunWith(SpringRunner.class)
class WebapiApplicationTests {

    @Autowired
    private StudentService studentService;

    //@Test
    void contextLoads() {

//        StudentBean studentBean = studentService.getInfo(1);
//        System.out.println(studentBean.getAge());

        StudentBean sb = new StudentBean();
        sb.setAge(8);
        sb.setName("xiaobai");
        Integer row = studentService.insertOne(sb);
        System.out.println("row: " +row);
    }
}
