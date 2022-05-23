package com.energysolution.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
	
	//null 전용
	public TotalBillDTO(int BillId) {
		this.BillId = BillId;
	}
}
