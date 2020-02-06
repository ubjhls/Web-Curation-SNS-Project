package com.web.curation.service;

import java.util.List;

import com.web.curation.model.follow.Follow;

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
	List<Follow> getFollower(int num);
	List<Follow> getFollowing(int num);
}