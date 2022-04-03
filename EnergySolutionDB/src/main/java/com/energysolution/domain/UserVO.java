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
}
