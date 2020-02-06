package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.PostDaoImpl;
import com.web.curation.model.post.Post;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	private PostDaoImpl postdao;
	
	@Override
	public int insertPost(Post post) {
		return postdao.insertPost(post);
	}

	@Override
	public List<Post> getAllPost(int num) {
		return postdao.getAllPost(num);
	}
	
	@Override
	public int likeCountUp(int num) {
		return postdao.likeCountUp(num);
	}

	@Override
	public int likeCountDown(int num) {
		return postdao.likeCountDown(num);
	}
	
	@Override
	public int commentCountUp(int num) {
		return postdao.commentCountUp(num);
	}

	@Override
	public int commentCountDown(int num) {
		return postdao.commentCountDown(num);
	}

	@Override
	public int getAuthor(int num) {
		return postdao.getAuthor(num);
	}

	@Override
	public List<Post> getMyPost(int num) {
		return postdao.getMyPost(num);
	}

	@Override
	public List<Post> getMyLikePost(int num) {
		return postdao.getMyLikePost(num);
	}

	@Override
	public List<Post> getMyCurationPost(int num) {
		return postdao.getMyCurationPost(num);
	}

}
