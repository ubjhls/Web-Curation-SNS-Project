package com.web.curation.model.statistics;

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
public class Statistics {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;

	private int post_count;
	private int scrap_count;
	private int like_count;
	private int likeClick_count;
	private int comment_count;
	private int commentWrite_count;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPost_count() {
		return post_count;
	}
	public void setPost_count(int post_count) {
		this.post_count = post_count;
	}
	public int getScrap_count() {
		return scrap_count;
	}
	public void setScrap_count(int scrap_count) {
		this.scrap_count = scrap_count;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getLikeClick_count() {
		return likeClick_count;
	}
	public void setLikeClick_count(int likeClick_count) {
		this.likeClick_count = likeClick_count;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public int getCommentWrite_count() {
		return commentWrite_count;
	}
	public void setCommentWrite_count(int commentWrite_count) {
		this.commentWrite_count = commentWrite_count;
	}
	
	@Override
	public String toString() {
		return "Statistics [num=" + num + ", post_count=" + post_count + ", scrap_count=" + scrap_count
				+ ", like_count=" + like_count + ", likeClick_count=" + likeClick_count + ", comment_count="
				+ comment_count + ", commentWrite_count=" + commentWrite_count + "]";
	}
	
	
}
