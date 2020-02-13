package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.CommentDaoImpl;
import com.web.curation.model.comment.Comment;

@Service
public class CommentService implements ICommentService {

	@Autowired
	private CommentDaoImpl commentdao;
	
	@Override
	public List<Comment> getAllComment(int num) {
		return commentdao.getAllComment(num);
	}
	
	@Override
	public int insertComment(Comment comment) {
		return commentdao.insertComment(comment);
	}

	@Override
<<<<<<< HEAD
	public int deleteComment(Comment comment) {
		return commentdao.deleteComment(comment);
=======
	public int deleteComment(int num) {
		return commentdao.deleteComment(num);
	}

	@Override
	public int getCommentCount(int num) {
		return commentdao.getCommentCount(num);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
	}

}
