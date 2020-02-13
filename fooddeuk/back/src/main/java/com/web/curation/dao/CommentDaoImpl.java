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
	
<<<<<<< HEAD
	public int deleteComment(Comment comment) {
		return sqlSession.delete(ns+"deleteComment", comment);
	}

=======
	public int deleteComment(int num) {
		return sqlSession.delete(ns+"deleteComment", num);
	}

	public int getCommentCount(int num) {
		return sqlSession.selectOne(ns+"getCommentCount", num);
	}
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
	
}
