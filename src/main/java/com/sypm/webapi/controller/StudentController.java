package com.sypm.webapi.controller;

import com.sypm.webapi.bean.StudentBean;
import com.sypm.webapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/webServiceDemo")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/getOneStudentInfo", method = RequestMethod.GET)
    @ResponseBody
    public StudentBean getOneStudentInfo(Integer id){

        StudentBean studentBean = studentService.getInfo(id);
        return studentBean;
    }

    @RequestMapping(value = "/insertStudentBean", method = RequestMethod.POST)
    @ResponseBody
    public String insertSudent(@RequestBody StudentBean studentBean) {

        Integer row = studentService.insertOne(studentBean);
        if (row > 0) {
            return "insert success...";
        }else {
            return "insert failed...";
        }
    }
}
