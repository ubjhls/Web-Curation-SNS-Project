package com.web.curation.model.post;

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
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;

	private int author;
	private String title;
	private String content;
	private String date; // DB에서 now()로 대체
	private int count_like;
	private int count_comment;
	private int count_star;
	private String address;
	private String image;
	private String type;
	private int scrap_author;
	private String nickname;
	private int mynum;
	private int islike;

	public Post() {
		super();
	}
	
	public Post(int num, String address) {
		super();
		this.num = num;
		this.address = address;
	}
	
	public Post(int num, int author, String date) {
		super();
		this.num = num;
		this.author = author;
		this.date = date;
	}

	public Post(int author, String title, String content, int count_star, String address) {
		super();
		this.author = author;
		this.title = title;
		this.content = content;
		this.count_star = count_star;
		this.address = address;
	}
	
	public Post(int num, int author, String title, String content, String date, int count_star, String address) {
		super();
		this.num = num;
		this.author = author;
		this.title = title;
		this.content = content;
		this.date = date;
		this.count_star = count_star;
		this.address = address;
	}

	public Post(int num, int author, String title, String content, String date, int count_like, int count_comment,
			int count_star, String address) {
		super();
		this.num = num;
		this.author = author;
		this.title = title;
		this.content = content;
		this.date = date;
		this.count_like = count_like;
		this.count_comment = count_comment;
		this.count_star = count_star;
		this.address = address;
	}
	
	

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCount_like() {
		return count_like;
	}
	public void setCount_like(int count_like) {
		this.count_like = count_like;
	}
	public int getCount_comment() {
		return count_comment;
	}
	public void setCount_comment(int count_comment) {
		this.count_comment = count_comment;
	}
	public int getCount_star() {
		return count_star;
	}
	public void setCount_star(int count_star) {
		this.count_star = count_star;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getIslike() {
		return islike;
	}
	public void setIslike(int islike) {
		this.islike = islike;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getScrap_author() {
		return scrap_author;
	}
	public void setScrap_author(int scrap_author) {
		this.scrap_author = scrap_author;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getMynum() {
		return mynum;
	}
	public void setMynum(int mynum) {
		this.mynum = mynum;
	}

	@Override
	public String toString() {
		return "Post [num=" + num + ", author=" + author + ", title=" + title + ", content=" + content + ", date="
				+ date + ", count_like=" + count_like + ", count_comment=" + count_comment + ", count_star="
				+ count_star + ", address=" + address + ", image=" + image + ", type=" + type + ", scrap_author="
				+ scrap_author + ", nickname=" + nickname + ", mynum=" + mynum + ", islike=" + islike + "]";
	}
	
	
}
