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
	
	public int likeCountUp(int num) {
		return sqlSession.update(ns+"likeCountUp", num);
	}
	
	public int likeCountDown(int num) {
		return sqlSession.update(ns+"likeCountDown", num);
	}
	
	public int commentCountUp(int num) {
		return sqlSession.update(ns+"commentCountUp", num);
	}
	
	public int commentCountDown(int num) {
		return sqlSession.update(ns+"commentCountDown", num);
	}
	
	public int getAuthor(int num) {
		return sqlSession.selectOne(ns+"getAuthor", num);
	}
}
