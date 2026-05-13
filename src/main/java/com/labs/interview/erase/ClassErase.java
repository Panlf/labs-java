package com.labs.interview.erase;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型擦除测试
 * @author panlf
 * @date 2026/5/13
 */
public class ClassErase {
    /**
     * 既然泛型擦除有问题为什么还要使用呢？用Object代替不行？
     * 1、使用泛型可以在编译期间进行类型检测，尽早发现问题
     * 2、使用Object类型需要手动强制转换类型，而用泛型则可以节省了这个操作可以有效避免 classCastException，代码可读性更高，出错率更低
     * 3、提升性能，编译完成后，基本就确定了类型，节省了强制类型转换带来的性能消耗
     */
    public static void main(String[] args) {
        //compareClass();
        getTClass();
    }

    public static void compareClass(){

        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        // true
        System.out.println(list1.getClass() == list2.getClass());
        System.out.println(list1.getClass());
        System.out.println(list2.getClass());
    }

    public static void reflectClass(){
        try {
            //泛型只在编译期间起作用
            List<Integer> list1 = new ArrayList<>();

            list1.add(1);
            //[1, 学习Java]
            Method method = list1.getClass().getDeclaredMethod("add", Object.class);

            method.invoke(list1, "学习Java");

            System.out.println(list1);
        }catch (Exception  e){
            e.printStackTrace();
        }
    }


    public static void getTClass(){
        // 编译器在编译期间会动态的将泛型T 擦除为 Object ，或者将 T extends xxxClass 擦除为其限定类型 xxxClass
        Class<Student> studentClass = Student.class;

        Field[] fields = studentClass.getDeclaredFields();

        for(Field field:fields){
            System.out.println(field.getName()+":"+field.getType());
        }
    }
}
