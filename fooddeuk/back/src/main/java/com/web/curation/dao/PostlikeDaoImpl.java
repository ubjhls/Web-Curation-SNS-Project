package com.web.curation.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.postlike.Postlike;

@Repository
public class PostlikeDaoImpl {

	String ns="postlike.";
	@Autowired
	private SqlSession sqlSession;
	
	public int like(Postlike like) {
		return sqlSession.insert(ns+"like", like);
	}
	public int unlike(Postlike like) {
		return sqlSession.delete(ns+"unlike", like);
	}

	public int checkLike(Postlike like) {
		return sqlSession.selectOne(ns+"checkLike", like);
	}
	public int getCountLike(int num) {
		return sqlSession.selectOne(ns+"getCountLike", num);
	}
	
}
