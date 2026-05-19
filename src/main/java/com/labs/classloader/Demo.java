package com.labs.classloader;

/**
 * 测试类
 * @author panlf
 * @date 2026/5/19
 */
public class Demo {
	public Demo() {
		System.out.println("Hello ClassLoader -- "+this.getClass().getClassLoader());
	}
}
