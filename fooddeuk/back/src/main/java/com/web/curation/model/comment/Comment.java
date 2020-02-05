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
	
	private int author;
	private String comment;
	private String date; // DB에서 now()로 대체
	private String nickname;
	
	public Comment() {
		super();
	}
	
	public Comment(int num, int author, String comment) {
		super();
		this.num = num;
		this.author = author;
		this.comment = comment;
	}
	
	public Comment(int num, int author, String comment, String date) {
		super();
		this.num = num;
		this.author = author;
		this.comment = comment;
		this.date = date;
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

	@Override
	public String toString() {
		return "Comment [num=" + num + ", author=" + author + ", comment=" + comment + ", date=" + date + ", nickname="
				+ nickname + "]";
	}

	
}
