package com.web.curation.service;

import java.util.List;

import com.web.curation.model.post.Post;

public interface IPostService {
	int insertPost(Post post);
	List<Post> getAllPost(int num);
}
