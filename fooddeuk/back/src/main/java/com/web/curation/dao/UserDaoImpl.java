package com.web.curation.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.user.User;

@Repository
public class UserDaoImpl {

	String ns="user.";
	@Autowired
	private SqlSession sqlSession;
	
	public User getUserByEmail(String email) {
		return sqlSession.selectOne(ns+"getUserByEmail", email);
	}
	public User findUserByEmailAndPassword(User user) {
		return sqlSession.selectOne(ns+"findUserByEmailAndPassword", user);
	}
	public int signUp(User user) {
		return sqlSession.insert(ns+"signUp", user);
	}
	public String checkNick(String nickname) {
		return sqlSession.selectOne(ns+"checkNick", nickname);
	}
	public String checkEmail(String email) {
		return sqlSession.selectOne(ns+"checkEmail", email);
	}
	public String getConfirm(String email) {
		return sqlSession.selectOne(ns+"getConfirm", email);
	}
	public int updateConfirm(User user) {
		return sqlSession.update(ns+"updateConfirm", user);
	}
	public String getKey(String email) {
		return sqlSession.selectOne(ns+"getKey", email);
	}
	public int updateKey(User user) {
		return sqlSession.update(ns+"updateKey", user);
	}
	public int updatePassword(User user) {
		return sqlSession.update(ns+"updatePassword", user);
	}
	public String getSocial(String email) {
		return sqlSession.selectOne(ns+"getSocial", email);
	}
	public int successJoin(String email) {
		return sqlSession.update(ns+"successJoin", email);
	}
	public int getNumByEmail(String email) {
		return sqlSession.selectOne(ns+"getNumByEmail", email);
	}
	public int getNumByNickname(String nickname) {
		return sqlSession.selectOne(ns+"getNumByNickname", nickname);
	}
	public int updateUser(User user) {
		return sqlSession.update(ns+"updateUser", user);
	}
	public User getUserByNickname(String nickname) {
		return sqlSession.selectOne(ns+"getUserByNickname", nickname);
	}
	public int getAuth(int num) {
		return sqlSession.selectOne(ns+"getAuth", num);
	}
	
}
