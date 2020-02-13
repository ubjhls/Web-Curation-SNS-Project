package com.web.curation.model.postlike;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

public class Postlike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postnum;

	private int liker;
	private String date; // DB에서 now()로 대체
	
	public Postlike() {
		super();
	}
	
	public Postlike(int postnum, int liker, String date) {
		super();
		this.postnum = postnum;
		this.liker = liker;
		this.date = date;
	}
	
	public Postlike(int postnum, int liker) {
		super();
		this.postnum = postnum;
		this.liker = liker;
	}

	public int getPostnum() {
		return postnum;
	}

	public void setPostnum(int postnum) {
		this.postnum = postnum;
	}

	public int getLiker() {
		return liker;
	}

	public void setLiker(int liker) {
		this.liker = liker;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Postlike [postnum=" + postnum + ", liker=" + liker + ", date=" + date + "]";
	}
	
	
}
