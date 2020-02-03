// 디비 셋팅 주석 
package com.web.curation.model.search;

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
public class Search {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;

	private int who;

	public Search() {
		super();
	}

	public Search(int num, int who) {
		super();
		this.num = num;
		this.who = who;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getWho() {
		return who;
	}

	public void setWho(int who) {
		this.who = who;
	}

	@Override
	public String toString() {
		return "Profile [num=" + num + ", who=" + who + "]";
	}
	
}
