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
	public int deleteComment(Comment comment) {
		return commentdao.deleteComment(comment);
	}

	@Override
	public int getCommentCount(int num) {
		return commentdao.getCommentCount(num);
	}

}
