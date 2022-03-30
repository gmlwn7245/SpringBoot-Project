package com.energysolution.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private Long id;			//UserId
	private String name;		//UserName
	private String email;		//UserEmail	
	private String password;	//UserPW
			
	//출력
	public long getUserId() {
		return id;
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
	public void setUserId(long id) {
		this.id = id;
	}
	public void setUserName(String name) {
		this.name = name;
	}	
	public void setUserEmail(String email) {
		this.email = email;
	}	
	public void setUserPW(String password) {
		this.password = password;
	}	
}
