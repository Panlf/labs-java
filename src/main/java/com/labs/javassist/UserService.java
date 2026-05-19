package com.labs.javassist;

/**
 * 用户服务类
 * @author panlf
 * @date 2026/5/19
 */
public class UserService {

	public void updateUser(long id) {
		System.out.println("update data the id is "+id);
	}
	
	public void addUser(String name,String age) {
		System.out.println("add user...");
	}
}
