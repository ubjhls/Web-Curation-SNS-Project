package com.web.curation.model.curation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

public class Curation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;

	private String place;

	public Curation() {
		super();
	}

	public Curation(int num, String place) {
		super();
		this.num = num;
		this.place = place;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	@Override
	public String toString() {
		return "Curation [num=" + num + ", place=" + place + "]";
	}
	
}
