package com.energysolution.domain;

import lombok.Getter;
import java.sql.Date;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailBillDTO {
	
	//필요한 필드값
	private int BillId;
	private int Waterfee;
	private int Heatingfee;
	private int Electricityfee;
	//해당 field의 요금값
	
	
	//출력
	public int getWaterFee() {
		return Waterfee;
	}
	public int getHeatingFee() {
		return Heatingfee;
	}
	public int getElectricityFee() {
		return Electricityfee;
	}
	
	//입력
	public void setElectricityfee(int Electricityfee) {
		this.Electricityfee=Electricityfee;
	}
	public void setHeatingfee(int Heatingfee) {
		this.Heatingfee=Heatingfee;
	}
	public void setWaterfee(int Waterfee) {
		this.Waterfee=Waterfee;
	}

}
