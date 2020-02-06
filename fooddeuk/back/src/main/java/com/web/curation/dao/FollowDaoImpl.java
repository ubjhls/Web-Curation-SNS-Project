package com.web.curation.dao;

import java.util.List;

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
		if(sqlSession.selectOne(ns+"countFollower", num) == null)
			return 0;
		return sqlSession.selectOne(ns+"countFollower", num);
	}
	
	public int countFollowing(int num) {
		if(sqlSession.selectOne(ns+"countFollowing", num) == null)
			return 0;
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
	
	public int followerUp(int num) {
		return sqlSession.update(ns+"followerUp", num);
	}
	
	public int followerDown(int num) {
		return sqlSession.update(ns+"followerDown", num);
	}
	
	public int followingUp(int num) {
		return sqlSession.update(ns+"followingUp", num);
	}
	
	public int followingDown(int num) {
		return sqlSession.update(ns+"followingDown", num);
	}

	public List<Follow> getFollower(int num) {
		return sqlSession.selectList(ns+"getFollower", num);
	}
	public List<Follow> getFollowing(int num) {
		return sqlSession.selectList(ns+"getFollowing", num);
	}
	
}
