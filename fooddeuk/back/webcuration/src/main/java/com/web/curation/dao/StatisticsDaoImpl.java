package com.web.curation.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.statistics.Statistics;

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
	// ---------------------
	public int getPostCountForDate(Statistics stats) {
		if(sqlSession.selectOne(ns+"getPostCountForDate", stats) == null)
			return 0;
		return sqlSession.selectOne(ns+"getPostCountForDate", stats);
	}
	
	public int getScrapCountForDate(Statistics stats) {
		if(sqlSession.selectOne(ns+"getScrapCountForDate", stats) == null)
			return 0;
		return sqlSession.selectOne(ns+"getScrapCountForDate", stats);
	}
	
	public int getLikeCountForDate(Statistics stats) {
		if(sqlSession.selectOne(ns+"getLikeCountForDate", stats) == null)
			return 0;
		return sqlSession.selectOne(ns+"getLikeCountForDate", stats);
	}
	
	public int getLikeClickCountForDate(Statistics stats) {
		if(sqlSession.selectOne(ns+"getLikeClickCountForDate", stats) == null)
			return 0;
		return sqlSession.selectOne(ns+"getLikeClickCountForDate", stats);
	}
	
	public int getCommentCountForDate(Statistics stats) {
		if(sqlSession.selectOne(ns+"getCommentCountForDate", stats) == null)
			return 0;
		return sqlSession.selectOne(ns+"getCommentCountForDate", stats);
	}
	
	public int getCommentWriteCountForDate(Statistics stats) {
		if(sqlSession.selectOne(ns+"getCommentWriteCountForDate", stats) == null)
			return 0;
		return sqlSession.selectOne(ns+"getCommentWriteCountForDate", stats);
	}
	
	

}
