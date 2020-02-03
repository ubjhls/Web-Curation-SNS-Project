// 디비 셋팅 주석 
package com.web.curation.model.profile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;

	private int count_like;
	private int count_follower;
	private int count_following;
	private String place;
	private String picture;
	
	public Profile() {
		super();
	}
	
	public Profile(int num, String interest) {
		super();
		this.num = num;
		this.place = interest;
	}

	public Profile(int num, int count_like, int count_follower, int count_following, String interest) {
		super();
		this.num = num;
		this.count_like = count_like;
		this.count_follower = count_follower;
		this.count_following = count_following;
		this.place = interest;
	}

	public Profile(int num, int count_like, int count_follower, int count_following) {
		super();
		this.num = num;
		this.count_like = count_like;
		this.count_follower = count_follower;
		this.count_following = count_following;
	}

	public Profile(int num, int count_like, int count_follower, int count_following, String interest, String picture) {
		super();
		this.num = num;
		this.count_like = count_like;
		this.count_follower = count_follower;
		this.count_following = count_following;
		this.place = interest;
		this.picture = picture;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getCount_like() {
		return count_like;
	}

	public void setCount_like(int count_like) {
		this.count_like = count_like;
	}

	public int getCount_follower() {
		return count_follower;
	}

	public void setCount_follower(int count_follower) {
		this.count_follower = count_follower;
	}

	public int getCount_following() {
		return count_following;
	}

	public void setCount_following(int count_following) {
		this.count_following = count_following;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "Profile [num=" + num + ", count_like=" + count_like + ", count_follower=" + count_follower
				+ ", count_following=" + count_following + ", place=" + place + ", picture=" + picture + "]";
	}

	
}
