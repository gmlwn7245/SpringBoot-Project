package com.energysolution.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private String UserId;			//UserId
	private String name;		//UserName
	private String email;		//UserEmail	
	private String password;	//UserPW
			
	//출력
	public String getUserId() {
		return UserId;
	}		
	public String getUserName() {
		return name;
	}		
	public String getUserEmail() {
		return email;
	}		
	public String getUserPassword() {
		return password;
	}
		
	//설정
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}
	public void setUserName(String name) {
		this.name = name;
	}	
	public void setUserEmail(String email) {
		this.email = email;
	}	
	public void setUserPassword(String password) {
		this.password = password;
	}	
}
