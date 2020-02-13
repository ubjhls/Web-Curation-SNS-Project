package com.web.curation.model.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;
	
	private String email;
	private String nickname;
	private String name;
	private String password;
	private String intro;
	private int auth;
	private String confirm;
	private int social;
	private String temp;

	@Column(insertable = false, updatable = false)
	private LocalDateTime createDate;

	public User() {
		super();
	}
	
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public User(String email, String confirm, int temp) {
		super();
		this.email = email;
		this.confirm = confirm;
	}
	
	public User(String email, String temp, int temp1, int temp2) {
		super();
		this.email = email;
		this.temp = temp;
	}
	
	public User(String email, String nickname, String intro, int auth) {
		super();
		this.email = email;
		this.nickname = nickname;
		this.intro = intro;
		this.auth = auth;
	}

	public User(String email, String nickname, String name, String password, String intro) {
		super();
		this.email = email;
		this.nickname = nickname;
		this.name = name;
		this.password = password;
		this.intro = intro;
	}

	public User(String email, String nickname, String name, String password, String intro, String confirm) {
		super();
		this.email = email;
		this.nickname = nickname;
		this.name = name;
		this.password = password;
		this.intro = intro;
		this.confirm = confirm;
	}
	
	public User(String email, String nickname, String name, String password, String intro, String confirm, int social) {
		super();
		this.email = email;
		this.nickname = nickname;
		this.name = name;
		this.password = password;
		this.intro = intro;
		this.confirm = confirm;
		this.social = social;
	}

	public User(int num, String email, String nickname, String name, String password, String intro, int auth,
			String confirm, int social, String temp, LocalDateTime createDate) {
		super();
		this.num = num;
		this.email = email;
		this.nickname = nickname;
		this.name = name;
		this.password = password;
		this.intro = intro;
		this.auth = auth;
		this.confirm = confirm;
		this.social = social;
		this.temp = temp;
		this.createDate = createDate;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public int getSocial() {
		return social;
	}

	public void setSocial(int social) {
		this.social = social;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String gettemp() {
		return temp;
	}

	public void settemp(String temp) {
		this.temp = temp;
	}

	@Override
	public String toString() {
		return "User [num=" + num + ", email=" + email + ", nickname=" + nickname + ", name=" + name + ", password="
				+ password + ", intro=" + intro + ", auth=" + auth + ", confirm=" + confirm + ", social=" + social
				+ ", temp=" + temp + ", createDate=" + createDate + "]";
	}
	
	
}
