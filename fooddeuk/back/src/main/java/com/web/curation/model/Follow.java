package com.web.curation.model;

public class Follow {
	private int follower;
	private int following;
	public Follow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Follow(int follower, int following) {
		super();
		this.follower = follower;
		this.following = following;
	}
	@Override
	public String toString() {
		return "Follow [follower=" + follower + ", following=" + following + "]";
	}
	public int getFollower() {
		return follower;
	}
	public void setFollower(int follower) {
		this.follower = follower;
	}
	public int getFollowing() {
		return following;
	}
	public void setFollowing(int following) {
		this.following = following;
	}
}
