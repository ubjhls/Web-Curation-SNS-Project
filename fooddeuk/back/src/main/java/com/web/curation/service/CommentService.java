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
	
	public int insertComment(int num) {
		return commentdao.insertComment(num);
	}

}
