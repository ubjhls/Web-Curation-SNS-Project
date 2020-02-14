package com.web.curation.model.comment;

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
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;
	
	private int postnum;
	private int author;
	private String comment;
	private String date; // DB에서 now()로 대체
	private String nickname;
	private boolean iscomment;
	
	public Comment() {
		super();
	}
	
	public Comment(int num, int postnum, int author) {
		super();
		this.num = num;
		this.postnum = postnum;
		this.author = author;
	}

	public Comment(int postnum, int author, String comment) {
		super();
		this.postnum = postnum;
		this.author = author;
		this.comment = comment;
	}

	public Comment(int num, int postnum, int author, String comment) {
		super();
		this.num = num;
		this.postnum = postnum;
		this.author = author;
		this.comment = comment;
	}
	
	public Comment(int num, int postnum, int author, String comment, String date, String nickname, boolean iscomment) {
		super();
		this.num = num;
		this.postnum = postnum;
		this.author = author;
		this.comment = comment;
		this.date = date;
		this.nickname = nickname;
		this.iscomment = iscomment;
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPostnum() {
		return postnum;
	}
	public void setPostnum(int postnum) {
		this.postnum = postnum;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public boolean isIscomment() {
		return iscomment;
	}
	public void setIscomment(boolean iscomment) {
		this.iscomment = iscomment;
	}
	
	@Override
	public String toString() {
		return "Comment [num=" + num + ", postnum=" + postnum + ", author=" + author + ", comment=" + comment
				+ ", date=" + date + ", nickname=" + nickname + ", iscomment=" + iscomment + "]";
	}
	
}
