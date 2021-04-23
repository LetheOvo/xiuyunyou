package com.lut.dao;

import java.util.List;

import com.lut.entity.User;

public interface ForgameDao {
	public List<User> Query();
	//校验登录信息
	public List<User> StuLogin(String username,String password);
	public int inster(User record);
}
