package com.energysolution.domain;

import lombok.Getter;
import java.sql.Date;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillVO {
	private String BillId;
	private Date Billdate;
	private Long ElectricityFee;
	private Long HeatingFee;
	private Long WaterFee;	
	//private Long totalFee;
}
