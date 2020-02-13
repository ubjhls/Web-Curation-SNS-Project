package com.web.curation.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StatisticsDaoImpl {
	String ns="statistics.";
	@Autowired
	private SqlSession sqlSession;
	
	public int getPostCount(int num) {
		return sqlSession.selectOne(ns+"getPostCount", num);
	}
	
	public int getScrapCount(int num) {
		return sqlSession.selectOne(ns+"getScrapCount", num);
	}
	
	public int getLikeCount(int num) {
		return sqlSession.selectOne(ns+"getLikeCount", num);
	}
	
	public int getLikeClickCount(int num) {
		return sqlSession.selectOne(ns+"getLikeClickCount", num);
	}
	
	public int getCommentCount(int num) {
		return sqlSession.selectOne(ns+"getCommentCount", num);
	}
	
	public int getCommentWriteCount(int num) {
		return sqlSession.selectOne(ns+"getCommentWriteCount", num);
	}
	
	

}
