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
	Post getPost(Post post);
	int scrapPost(Post post);
	int deletePost(int num);
<<<<<<< HEAD
=======
	List<Post> getMyFollowingPost(int num);
	List<Post> getMyMainPost(Curation curation);
	int updatePost(Post post);
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
}
