package com.energysolution.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetailBillDTO {
	
	//필요한 필드값
	private int BillId;
	private int WaterFee;
	private int WaterUsage;
	private int ElectricityFee;
	private int ElectricityUsage;
	

}
