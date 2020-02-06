package com.web.curation.service;

import java.util.List;

import com.web.curation.model.post.Post;

public interface IPostService {
	int insertPost(Post post);
	List<Post> getAllPost(int num);
	int likeCountUp(int num);
	int likeCountDown(int num);
	int commentCountUp(int num);
	int commentCountDown(int num);
	int getAuthor(int num);
	List<Post> getMyPost(int num);
	List<Post> getMyLikePost(int num);
	List<Post> getMyCurationPost(int num);
}
