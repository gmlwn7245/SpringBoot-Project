package com.energysolution.domain;

import lombok.Getter;
import java.sql.Date;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailBillVO {
	//
	private int BillId;
	
	// 분리 필요
	private int WaterFee;	
	private int HeatingFee;
	private int ElectricityFee;
	
	
	//출력
	public int getBillId() {
		return BillId;
	}
	public int getWaterFee() {
		return WaterFee;
	}
	public int getHeatingFee() {
		return HeatingFee;
	}
	public int getElectricityFee() {
		return ElectricityFee;
	}
	
	//입력
	public void setBillId(int BillId) {
		this.BillId = BillId;
	}
	public void setElectricityFee(int ElectricityFee) {
		this.ElectricityFee=ElectricityFee;
	}
	public void setHeatingFee(int HeatingFee) {
		this.HeatingFee=HeatingFee;
	}
	public void setWaterFee(int WaterFee) {
		this.WaterFee=WaterFee;
	}

}
