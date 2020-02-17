package com.web.curation.model.post;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
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
	private String image; // 게시물 사진
	private int islike;
	private String type;
	private int scrapnum;
	private String nickname;
	private String picture; // 사용자 사진
	private String scraptitle;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getScrapnum() {
		return scrapnum;
	}

	public void setScrapnum(int scrapnum) {
		this.scrapnum = scrapnum;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getIslike() {
		return islike;
	}

	public void setIslike(int islike) {
		this.islike = islike;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getScraptitle() {
		return scraptitle;
	}

	public void setScraptitle(String scraptitle) {
		this.scraptitle = scraptitle;
	}

	@Override
	public String toString() {
		return "Post [num=" + num + ", author=" + author + ", title=" + title + ", content=" + content + ", date="
				+ date + ", count_like=" + count_like + ", count_comment=" + count_comment + ", count_star="
				+ count_star + ", address=" + address + ", image=" + image + ", islike=" + islike + ", type=" + type
				+ ", scrapnum=" + scrapnum + ", nickname=" + nickname + ", picture=" + picture + ", scraptitle="
				+ scraptitle + "]";
	}

	
}
