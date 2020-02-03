package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.post.Post;

@Repository
public class PostDaoImpl {

	String ns="post.";
	@Autowired
	private SqlSession sqlSession;
	
	public int insertPost(Post post) {
		return sqlSession.insert(ns+"insertPost", post);
	}
	
	public List<Post> getAllPost(int num) {
		return sqlSession.selectList(ns+"getAllPost", num);
	}
	
	
}
