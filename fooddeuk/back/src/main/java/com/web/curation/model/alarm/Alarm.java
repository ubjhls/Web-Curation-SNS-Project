package com.web.curation.model.alarm;

public class Alarm {
	int num;
	String receiver;
    String sender;
	int reason;
	int confirm;
	String date;
	String picture;
	
	public Alarm() {

	}

	public Alarm(int num, String receiver, String sender, int reason, int confirm, String date) {
		super();
		this.num = num;
		this.receiver = receiver;
		this.sender = sender;
		this.reason = reason;
		this.confirm = confirm;
		this.date = date;
	}

	@Override
	public String toString() {
		return "Alarm [num=" + num + ", receiver=" + receiver + ", sender=" + sender + ", reason=" + reason
				+ ", confirm=" + confirm + ", date=" + date + "]";
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public int getReason() {
		return reason;
	}
	public void setReason(int reason) {
		this.reason = reason;
	}
	public int getConfirm() {
		return confirm;
	}
	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
	