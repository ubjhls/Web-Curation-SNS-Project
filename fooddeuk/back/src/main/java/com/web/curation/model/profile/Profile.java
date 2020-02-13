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
<<<<<<< HEAD
@NoArgsConstructor
=======

>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c
@AllArgsConstructor
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;

	private int count_follower;
	private int count_following;
	private String place;
	private String picture;
	private String placeFirst;
	private String placeSecond;
	
	public Profile() {
		super();
	}
	
	public Profile(int num, String interest) {
		super();
		this.num = num;
		this.place = interest;
	}

	public Profile(int num, int count_follower, int count_following, String interest) {
		super();
		this.num = num;
		this.count_follower = count_follower;
		this.count_following = count_following;
		this.place = interest;
	}

	public Profile(int num, int count_follower, int count_following) {
		super();
		this.num = num;
		this.count_follower = count_follower;
		this.count_following = count_following;
	}

	public Profile(int num, int count_like, int count_follower, int count_following, String interest, String picture) {
		super();
		this.num = num;
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
	public String getPlaceFirst() {
		return placeFirst;
	}
	public void setPlaceFirst(String placeFirst) {
		this.placeFirst = placeFirst;
	}
	public String getPlaceSecond() {
		return placeSecond;
	}
	public void setPlaceSecond(String placeSecond) {
		this.placeSecond = placeSecond;
	}

	@Override
	public String toString() {
		return "Profile [num=" + num + ", count_follower=" + count_follower + ", count_following=" + count_following
				+ ", place=" + place + ", picture=" + picture + ", placeFirst=" + placeFirst + ", placeSecond="
				+ placeSecond + "]";
	}

	
}
