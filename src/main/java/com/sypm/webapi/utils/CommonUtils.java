package com.sypm.webapi.utils;

import com.sypm.webapi.bean.StudentBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtils {

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n){

        List<List<T>> result = new ArrayList<List<T>>();

        // 计算余数
        int remaider = source.size() % n;
        //  计算商
        int number = source.size() / n;
        // 偏移量
        int offset = 0;

        for(int i = 0; i < n; i++){

            List<T> value = null;
            if(remaider > 0){

                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            }else{

                value=source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 将List集合中重复的值返回
     * @param items
     * @return
     */
    public static List<String> frequencyOfListElements(List<String> items ) {

        // 存储重复的值
        List<String> duplicateStringList = new ArrayList<>();

        if (items == null || items.size() == 0) return null;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String temp : items) {
            Integer count = map.get(temp);
            map.put(temp, (count == null) ? 1 : count + 1);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= 2) {

                duplicateStringList.add(entry.getKey());
            }
        }
        return duplicateStringList;
    }

    /**
     * 将数组中的值去重
     * @param list
     * @return
     */
    public static <T> List<T> removeDuplicateValue(List<T> list) {

        // 创建一个新的list集合，用于存储去重后的元素
        List listTemp = new ArrayList();
        // 遍历list集合
        for (int i = 0; i < list.size(); i++) {
            // 判断listTemp集合中是否包含list中的元素
            if (!listTemp.contains(list.get(i))) {
                // 将未包含的元素添加进listTemp集合中
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

    /**
     * 记录错误信息
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Map<T, String> recordErrorInfo(List<T> list) {

        Map<T, String> map = new HashMap<>();
        for (T t : list) {

            String errorInfo = null;
            try {

                errorInfo = DataLenCheckHelper.checkAttributeValueLen(t);
            } catch (Exception e) {

            }

            if (errorInfo != null) {

                map.put(t, errorInfo);
            }
        }
        return map;
    }

    public static Boolean stringIsNullOrZero(String str) {

        if ("".equals(str) || str == null) {

            return true;
        } else {

            return false;
        }
    }

//    public static void main(String[] args) throws Exception {
//
//        List<Integer> integers=new ArrayList<>();
//        integers.add(1);
//        integers.add(2);
//        integers.add(3);
//        integers.add(4);
//        integers.add(5);
//        List<List<Integer>> lists=averageAssign(integers, 2);
//        System.out.println(lists);
//
//        Integer id = 2;
//        integers.remove(id);
//        System.out.println("integers: " + integers);
//
//        List<String> stringList = new ArrayList<>();
//        stringList.add("xiao");
//        stringList.add("xiao");
//        stringList.add("hehe");
//
//        List<String> duplicatList = frequencyOfListElements(stringList);
//        System.out.println(duplicatList);
//
//        List<StudentBean> studentBeanList = new ArrayList<>();
//        StudentBean studentBean = new StudentBean();
//        studentBean.setId(1);
//        studentBean.setAge(23);
//        studentBeanList.add(studentBean);
//
//        Map<StudentBean, String> studentBeanStringMap = CommonUtils.recordErrorInfo(studentBeanList);
//        System.out.println(studentBeanStringMap);
//
//
//        Boolean flag = CommonUtils.stringIsNullOrZero(studentBean.getName());
//        System.out.println(flag);
//    }
}
