package com.sypm.webapi.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sypm.webapi.utils.DataLen;

public class StudentBean {

    private Integer id;

    @DataLen(3)
    private String name;

    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
