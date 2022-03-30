package com.energysolution.dto;

public class userDTO {
	public Long id;
	public String name;
	public String email;
	public String password;
	
	public long getUserId() {
		return id;
	}
	
	public void setUserId(long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return name;
	}
}
