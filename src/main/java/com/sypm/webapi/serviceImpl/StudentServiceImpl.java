package com.sypm.webapi.serviceImpl;

import com.sypm.webapi.bean.StudentBean;
import com.sypm.webapi.mapper.StudentMapper;
import com.sypm.webapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentBean getInfo(Integer id) {

        return studentMapper.getInfo(id);
    }

    @Override
    public Integer insertOne(StudentBean studentBean) {

        return studentMapper.insertOne(studentBean);
    }
}
