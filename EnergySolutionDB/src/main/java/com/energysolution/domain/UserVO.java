package com.energysolution.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserVO {
	private String UserId;
	private String Name;
	private String Password;
	private String Email;
	
	//출력
	public String getUserId() {
		return UserId;
	}	
	public String getUserName() {
		return Name;
	}
	public String getUserPassword() {
		return Password;
	}
	public String getUserEmail() {
		return Email;
	}
	//입력
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}
	public void setUserName(String Name) {
		this.Name = Name;
	}
	public void setUserPassword(String Password) {
		this.Password = Password;
	}
	public void setUserEmail(String Email) {
		this.Email = Email;
	}
    @Override
    public String toString() {
        return "UserVO [ UserId=" + UserId+ ",Name = "+Name+", Password=" + Password + ", Email=" + Email + "]";
    }

}
