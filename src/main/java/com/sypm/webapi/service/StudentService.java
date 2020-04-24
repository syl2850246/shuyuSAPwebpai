package com.sypm.webapi.service;

import com.sypm.webapi.bean.StudentBean;

public interface StudentService {

    StudentBean getInfo(Integer id);

    Integer insertOne(StudentBean studentBean);
}
