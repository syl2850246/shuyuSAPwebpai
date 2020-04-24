package com.sypm.webapi.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 *  校验数据长度
 */
public class DataLenCheckHelper {

    /**
     * 校验数据属性至
     *
     * @param obj
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static <T> String checkAttributeValueLen(T obj) throws Exception
    {
        if (null != obj)
        {
            // 得到class
            Class cls = obj.getClass();
//            System.out.println("校验对象中参数的数据长度是否符合要求,校验对象:" + cls.getName());

            // 得到所有属性
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++)
            {// 遍历
                try
                {
                    // 得到属性
                    Field field = fields[i];
                    Annotation[] anns = field.getAnnotations();
                    DataLen dataLen = null;
                    for (Annotation ann : anns)
                    {
                        if (ann instanceof DataLen)
                            dataLen = (DataLen) ann;
                    }

                    // 判断该属性是否有校验数据长度的注解
                    if (null != dataLen)
                    {
                        // 打开私有访问
                        field.setAccessible(true);
                        // 获取属性
                        String name = field.getName();
                        // 获取属性值
                        Object value = field.get(obj);
                        // 指定的长度
                        int len = dataLen.value();
                        // 数据的长度
                        int vaLen = 0;
                        String data = null;
                        // 一个个赋值
                        if (null != value && value instanceof String)
                        {
                            data = (String) value;
                            vaLen = data.length();
                        }

                        if (vaLen > len || (name.equals("MATNR") && CommonUtils.stringIsNullOrZero(value.toString())) || (name.equals("WERKS") && CommonUtils.stringIsNullOrZero(value.toString())) || (name.equals("KUNNR") && CommonUtils.stringIsNullOrZero(value.toString())) || (name.equals("LIFNR") && CommonUtils.stringIsNullOrZero(value.toString())) || (name.equals("KOSTL") && CommonUtils.stringIsNullOrZero(value.toString())))
                        {
//                            System.out.print("参数名:" + name + ",参数值:" + data + ",指定的数据长度应小于:" + len + "，实际长度:" + vaLen);
                            return "参数名:" + name + ",参数值:" + data + ",指定的数据长度应小于:" + len + "，实际长度:" + vaLen;
//                            throw new Exception();
                        }
                    }
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
