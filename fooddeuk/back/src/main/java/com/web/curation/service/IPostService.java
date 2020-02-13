package com.web.curation.service;

import java.util.List;

import com.web.curation.model.curation.Curation;
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
	List<Post> getMyCurationPost(Curation curation);
	List<Post> getAllUserPost(int num);
	Post getPost(int num);
	int scrapPost(Post post);
	int deletePost(int num);
	List<Post> getMyFollowingPost(int num);
	List<Post> getMyMainPost(Curation curation);
	int updatePost(Post post);
	List<Post> getMyScrapPost(int num);
}
