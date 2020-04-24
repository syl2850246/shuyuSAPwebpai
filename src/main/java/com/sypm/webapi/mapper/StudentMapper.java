package com.sypm.webapi.mapper;

import com.sypm.webapi.bean.StudentBean;
import org.springframework.stereotype.Component;

public interface StudentMapper {

    StudentBean getInfo(Integer id);

    Integer insertOne(StudentBean studentBean);
}
