package com.web.curation.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.follow.Follow;

@Repository
public class FollowDaoImpl {

	String ns="follow.";
	@Autowired
	private SqlSession sqlSession;
	
	public int countFollower(int num) {
		return sqlSession.selectOne(ns+"countFollower", num);
	}
	
	public int countFollowing(int num) {
		return sqlSession.selectOne(ns+"countFollowing", num);
	}
	
	public int checkFollow(Follow follow) {
		return sqlSession.selectOne(ns+"checkFollow", follow);
	}
	
	public int follow(Follow follow) {
		return sqlSession.insert(ns+"follow", follow);
	}
	
	public int unFollow(Follow follow) {
		return sqlSession.delete(ns+"unFollow", follow);
	}
	
}
