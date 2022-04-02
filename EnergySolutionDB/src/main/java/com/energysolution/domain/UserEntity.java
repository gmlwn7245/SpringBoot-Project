package com.energysolution.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserEntity {
	@Id
	private String UserId;
	
	private String Name;
	private String Password;
	private String Email;
	//test
	private String column1;
	private String column2;
}
