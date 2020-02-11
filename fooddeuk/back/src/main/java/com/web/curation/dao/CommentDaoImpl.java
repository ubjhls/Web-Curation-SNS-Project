package com.web.curation.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.comment.Comment;

@Repository
public class CommentDaoImpl {

	String ns="comment.";
	@Autowired
	private SqlSession sqlSession;
	
	public List<Comment> getAllComment(int num) {
		return sqlSession.selectList(ns+"getAllComment", num);
	}
	
	public int insertComment(Comment comment) {
		return sqlSession.insert(ns+"insertComment", comment);
	}
	
	public int deleteComment(Comment comment) {
		return sqlSession.delete(ns+"deleteComment", comment);
	}

	public int getCommentCount(int num) {
		return sqlSession.selectOne(ns+"getCommentCount", num);
	}
	
}
