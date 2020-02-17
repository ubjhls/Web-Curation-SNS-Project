package com.web.curation.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.profile.Profile;

@Repository
public class ProfileDaoImpl {

	String ns="profile.";
	@Autowired
	private SqlSession sqlSession;
	
	public int createProfile(int num) {
		return sqlSession.insert(ns+"createProfile", num);
	}
	
	public Profile getProfile(int num) {
		return sqlSession.selectOne(ns+"getProfile", num);
	}
	
	public String getMyPlace(int num) {
		return sqlSession.selectOne(ns+"getMyPlace", num);
	}
	
	public int updatePlace(Profile profile) {
		return sqlSession.update(ns+"updatePlace", profile);
	}
<<<<<<< HEAD
	
	public String getPicture(int num) {
		return sqlSession.selectOne(ns+"getPicture", num);
	}
	
=======
	public int insertPicture(String picture) {
		return sqlSession.selectOne(ns+"insertPicture", picture);
	}
>>>>>>> 97fe6e73d471b79ce126300c043d228c5906bf4f
}
