package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.PostDaoImpl;
import com.web.curation.model.curation.Curation;
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
	public List<Post> getMyCurationPost(Curation curation) {
		return postdao.getMyCurationPost(curation);
	}

	@Override
	public List<Post> getAllUserPost(int num) {
		return postdao.getAllUserPost(num);
	}

	@Override
	public Post getPost(int num) {
		return postdao.getPost(num);
	}

	@Override
	public int scrapPost(Post post) {
		return postdao.scrapPost(post);
	}

	@Override
	public int deletePost(int num) {
		return postdao.deletePost(num);
	}
	@Override
	public List<Post> getMyFollowingPost(int num) {
		return postdao.getMyFollowingPost(num);
	}

	@Override
	public List<Post> getMyMainPost(Curation curation) {
		return postdao.getMyMainPost(curation);
	}

	@Override
	public int updatePost(Post post) {
		return postdao.updatePost(post);
	}

	@Override
	public List<Post> getMyScrapPost(int num) {
		return postdao.getMyScrapPost(num);
	}

	@Override
	public String getPicture(int num) {
		return postdao.getPicture(num);
	}

}
