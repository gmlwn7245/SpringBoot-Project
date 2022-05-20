package com.energysolution.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TotalBillDTO {
	
	//필요한 필드값
	private String UserId;
	private int BillId;
	private String date;
	private String totalFee;
	private String electUse;
	private String waterUse;
	private String electFee;
	private String waterFee;
	private String publicFee;
}
