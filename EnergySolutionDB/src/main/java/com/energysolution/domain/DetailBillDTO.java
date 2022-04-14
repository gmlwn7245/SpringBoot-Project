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
	
	public DetailBillDTO(int BillId, int Waterfee,int Heatingfee,int Electricityfee) {
		this.BillId=BillId;
		this.Electricityfee=Electricityfee;
		this.Heatingfee=Heatingfee;
		this.Waterfee=Waterfee;
	}
	//출력
	public int getBillId() {
		return BillId;
	}
	public long getWaterFee() {
		return Waterfee;
	}
	public long getHeatingFee() {
		return Heatingfee;
	}
	public long getElectricityFee() {
		return Electricityfee;
	}
	
	//입력
	public void setBillId(int BillId) {
		this.BillId=BillId;
	}
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
