package com.web.curation.service;

import java.util.List;

import com.web.curation.model.follow.Follow;
import com.web.curation.model.user.User;

public interface IFollowService {
	int countFollower(int num);
	int countFollowing(int num);
	int checkFollow(Follow follow);
	int follow(Follow follow);
	int unFollow(Follow follow);
	int followerUp(int num);
	int followerDown(int num);
	int followingUp(int num);
	int followingDown(int num);
	List<User> getFollower(int num);
	List<User> getFollowing(int num);
}