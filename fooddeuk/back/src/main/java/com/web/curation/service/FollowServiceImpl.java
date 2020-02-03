package com.web.curation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD

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
=======
import org.springframework.transaction.annotation.Transactional;

import com.web.curation.dao.UserDaoImpl;
import com.web.curation.dao.followdao;
import com.web.curation.model.user.User;

@Service
public class FollowServiceImpl implements Ifollowservice {

	@Autowired
	private followdao followdao;
	
	
>>>>>>> 64061fcca4631267e8ae7fbc203700e7dfb1cb72

}
