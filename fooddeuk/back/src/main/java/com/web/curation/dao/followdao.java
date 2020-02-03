package com.web.curation.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.Follow;

@Repository
public class followdao {

	String ns = "fooddeuck.follow.";
	@Autowired
	private SqlSession sqlSession;
	public void addfollow(Follow follow) {
		sqlSession.insert(ns+"addfollow",follow);
	}
}
