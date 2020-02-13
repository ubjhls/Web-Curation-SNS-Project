package com.web.curation.service;

import java.util.List;

import com.web.curation.model.comment.Comment;

public interface ICommentService {
	List<Comment> getAllComment(int num);
	int insertComment(Comment comment);
<<<<<<< HEAD
	int deleteComment(Comment comment);
=======
	int deleteComment(int num);
	int getCommentCount(int num);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
}
