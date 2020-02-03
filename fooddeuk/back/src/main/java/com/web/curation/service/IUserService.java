package com.web.curation.service;

import com.web.curation.model.user.User;

public interface IUserService {
	User getUserByEmail(String email);
	User findUserByEmailAndPassword(User user);
	int signUp(User user);
	String checkNick(String nickname);
	String checkEmail(String email);
	String getConfirm(String email);
	int updateConfirm(User user);
	String getKey(String email);
	int updateKey(User user);
	int updatePassword(User user);
	String getSocial(String email);
	int successJoin(String email);
	int getNumByEmail(String email);
	int getNumByNickname(String nickname);
	int updateUser(User user);
	User getUserByNickname(String nickname);
	int getAuth(int num);
}
