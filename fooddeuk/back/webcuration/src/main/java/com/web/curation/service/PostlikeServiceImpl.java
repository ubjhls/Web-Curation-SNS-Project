package com.web.curation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.PostlikeDaoImpl;
import com.web.curation.model.postlike.Postlike;

@Service
public class PostlikeServiceImpl implements IPostlikeService {

	@Autowired
	private PostlikeDaoImpl postlikedao;
	
	@Override
	public int like(Postlike like) {
		return postlikedao.like(like);
	}

	@Override
	public int unlike(Postlike like) {
		return postlikedao.unlike(like);
	}

	@Override
	public int checkLike(Postlike like) {
		return postlikedao.checkLike(like);
	}

}
