package com.web.curation.service;

import java.util.List;

import com.web.curation.model.comment.Comment;

public interface ICommentService {
	List<Comment> getAllComment(int num);
	int insertComment(Comment comment);
	int deleteComment(int num);
	int getCommentCount(int num);
}
