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

}
