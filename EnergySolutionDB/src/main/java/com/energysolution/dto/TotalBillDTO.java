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
	private int TotalFee;
	private int WaterFee;
	private int WaterUsage;
	private int ElectricityFee;
	private int ElectricityUsage;
	
}
