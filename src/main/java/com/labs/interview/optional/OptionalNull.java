package com.labs.interview.optional;


import java.util.Optional;

/**
 * optional 处理null的实践
 * @author panlf
 * @date 2026/5/13
 */
public class OptionalNull {
    /*
     * 普遍来说，Optional普遍用于方法的返回类型 ， 表示方法可能不返回结果
     *
     *  不推荐使用的场景
     *      1、不应该用于类的字段 ， optional的创建和管理有一定的开销，在类中的字段使用会有一定的内存消耗，
     *         并且会使得对象的序列化变得复杂
     *      2、不应该用作方法的参数，会使得方法的使用和理解变得复杂
     *      3、不应该用作构造器参数，会迫使调用者创建Optional实例
     *      4、不应该用作集合的参数类型，集合已经可以很好的处理空值的情况，没必要使用Optional包装结合
     *      4、不建议使用.get()，容易报异常
     *      5、不建议.isPresent(){.get()}方法组合，已经显式调用，多此一举
     */

    public static void main(String[] args) {
        String value = "Lancer";

        //value如果是null，返回空指针异常
        Optional<String> optionalBox = Optional.of(value);

        //判断是否是NULL
        System.out.println(optionalBox.isPresent());

        //如果存在再进行数据处理
        optionalBox.ifPresent(v-> System.out.println(v));


        value = null;
        optionalBox = Optional.ofNullable(value);

        // 不管 是否为空，都执行
        System.out.println(optionalBox.orElse("Default User"));

        // 为空的时候才会执行
        System.out.println(optionalBox.orElseGet(() -> "Default User"));

        optionalBox.orElseThrow(()-> new RuntimeException("字符串不能为空"));

    }


}
