package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.web.curation.dao.FollowDaoImpl;
import com.web.curation.model.follow.Follow;
@Service
public class FollowServiceImpl implements IFollowService {
	@Autowired
	private FollowDaoImpl followdao;
	
	@Override
	public int countFollower(int num) {
		return followdao.countFollower(num);
	}

	@Override
	public int countFollowing(int num) {
		return followdao.countFollowing(num);
	}

	@Override
	public int checkFollow(Follow follow) {
		return followdao.checkFollow(follow);
	}

	@Override
	public int follow(Follow follow) {
		return followdao.follow(follow);
	}

	@Override
	public int unFollow(Follow follow) {
		return followdao.unFollow(follow);
	}

	@Override
	public int followerUp(int num) {
		return followdao.followerUp(num);
	}

	@Override
	public int followerDown(int num) {
		return followdao.followerDown(num);
	}

	@Override
	public int followingUp(int num) {
		return followdao.followingUp(num);
	}

	@Override
	public int followingDown(int num) {
		return followdao.followingDown(num);
	}

	@Override
	public List<Follow> getFollower(int num) {
		return followdao.getFollower(num);
	}
	@Override
	public List<Follow> getFollowing(int num) {
		return followdao.getFollowing(num);
	}
	
	
}