package com.lut.service;

import java.util.List;

import com.lut.entity.User;

public interface ForgameService {
		//�˺�����У��
		public List<User> StuLogin(String username, String password);
		/**
		 * ע���ʵ�ַ���
		 * @param user
		 * @return
		 */
		public int reg(User user);
}
