package com.web.curation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.UserDaoImpl;
import com.web.curation.model.user.User;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserDaoImpl userdao;
	
	@Override
	public User getUserByEmail(String email) {
		return userdao.getUserByEmail(email);
	}

	@Override
	public User findUserByEmailAndPassword(User user) {
		return userdao.findUserByEmailAndPassword(user);
	}

	@Override
	public int signUp(User user) {
		return userdao.signUp(user);
	}

	@Override
	public String checkNick(String nickname) {
		return userdao.checkNick(nickname);
	}

	@Override
	public String checkEmail(String email) {
		return userdao.checkEmail(email);
	}

	@Override
	public String getConfirm(String email) {
		return userdao.getConfirm(email);
	}
	
	@Override
	public int updateConfirm(User user) {
		return userdao.updateConfirm(user);
	}

	@Override
	public String getKey(String email) {
		return userdao.getKey(email);
	}

	@Override
	public int updateKey(User user) {
		return userdao.updateKey(user);
	}

	@Override
	public int updatePassword(String email) {
		return userdao.updatePassword(email);
	}

	@Override
	public String getSocial(String email) {
		return userdao.getKey(email);
	}

	@Override
	public int successJoin(String email) {
		return userdao.successJoin(email);
	}

	@Override
	public int getNumByEmail(String email) {
		return userdao.getNumByEmail(email);
	}

	@Override
	public int getNumByNickname(String nickname) {
		return userdao.getNumByNickname(nickname);
	}

	@Override
	public int updateUser(User user) {
		return userdao.updateUser(user);
	}

	@Override
	public int updateNewPassword(User user) {
		return userdao.updateNewPassword(user);
	}

	@Override
	public User getUserByNickname(String nickname) {
		return userdao.getUserByNickname(nickname);
	}


}
