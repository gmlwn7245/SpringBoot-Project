package com.energysolution.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDataDTO {
	private String date;
	private String electUse;
	private String waterUse;
	private String electFee;
	private String waterFee;
	private String publicFee;
	private String totalFee;
}
