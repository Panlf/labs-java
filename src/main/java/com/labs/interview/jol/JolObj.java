package com.labs.interview.jol;


import org.openjdk.jol.info.ClassLayout;

/**
 * 查看 Java 对象在 JVM 内存里的结构
 * @author panlf
 * @date 2026/5/13
 */
public class JolObj {
    public static void main(String[] args) {
        Object o = new Object();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
