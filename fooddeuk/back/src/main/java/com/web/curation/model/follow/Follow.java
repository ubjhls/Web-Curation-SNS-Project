package com.web.curation.model.follow;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int follower;
	private int following;
	// private Date date; // DB에서 now()로 대체
	
	
	public Follow() {
		super();
	}
	
	public Follow(int follower, int following) {
		super();
		this.follower = follower;
		this.following = following;
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
	
	@Override
	public String toString() {
		return "Follow [follower=" + follower + ", following=" + following + "]";
	}
	
	
	
}
