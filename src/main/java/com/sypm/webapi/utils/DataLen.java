package com.sypm.webapi.utils;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.TYPE })
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DataLen {

    int value();
}
