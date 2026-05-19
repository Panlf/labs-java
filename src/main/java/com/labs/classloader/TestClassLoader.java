package com.labs.classloader;

/**
 * 测试ClassLoader
 * @author panlf
 * @date 2026/5/19
 */
public class TestClassLoader {

	public static void main(String[] args) throws Exception{
		//用父类的ClassLoader
		//MyClassLoader myClassLoader = new MyClassLoader("F:\\Technology\\Eclipse\\workspace\\CommonMethod\\target\\classes\\");
		
		//用自己的ClassLoader
		MyClassLoader myClassLoader = new MyClassLoader(null,"C:\\CodeResource\\Java\\labs-java\\target\\classes\\");
		Class<?> cls = myClassLoader.loadClass("com.labs.classloader.Demo");
		cls.newInstance();
	}

}
