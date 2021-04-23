package com.lut.service.impl;

import java.util.List;

import com.lut.dao.ForgameDao;
import com.lut.dao.impl.ForgameDaoImpl;
import com.lut.entity.User;
import com.lut.service.ForgameService;

public class ForgameServiceImpl implements ForgameService{
	ForgameDao fd = new ForgameDaoImpl();

	public List<User> Query() {
		// TODO Auto-generated method stub
		return fd.Query();
	}

	public List<User> StuLogin(String username, String password) {
		// TODO Auto-generated method stub
		return fd.StuLogin(username, password);
	}

	@Override
	public int reg(User user) {
		// TODO Auto-generated method stub
		return fd.inster(user);
	}
	
}
