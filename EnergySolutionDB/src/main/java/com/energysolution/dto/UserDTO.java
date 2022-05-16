package com.energysolution.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
	private String UserId;
	private String Name;
	private String Password;
	private String Email;

	@Override
	public String toString() {
		return "UserVO [ UserId=" + UserId + ",Name = " + Name + ", Password=" + Password + ", Email=" + Email + "]";
	}

}
